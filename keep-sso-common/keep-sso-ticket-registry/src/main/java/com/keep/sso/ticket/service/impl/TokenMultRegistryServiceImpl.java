package com.keep.sso.ticket.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.keep.common.core.expection.BizExpection;
import com.keep.common.core.expection.TokenExpection;
import com.keep.sso.ticket.entity.*;
import com.keep.sso.ticket.enums.TicketTypeEnum;
import com.keep.sso.ticket.service.TokenRegistryService;
import com.keep.sso.ticket.entity.vo.OauthLoginVo;
import com.keep.sso.ticket.utils.EncodingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class TokenMultRegistryServiceImpl implements TokenRegistryService {


    public static final String TGT_PREFIX = "TGT-";

    public static final String AT_PREFIX = "AT-";

    public static final String RT_PREFIX = "RT-";

    public static final Long TGT_TIME_TO_LIVE = Long.valueOf(30 * 24 * 60 * 60);

    public static final Long RT_TIME_TO_LIVE = Long.valueOf(7 * 24 * 60 * 60);

    public static final Long AT_TIME_TO_LIVE = Long.valueOf(2 * 60 * 60);

    private AtomicLong count = new AtomicLong();

    @Autowired
    private TokenDbRegistryServiceImpl tokenDbRegistryService;
    @Autowired
    private TokenRedisRegistryServiceImpl tokenRedisRegistryService;

    @Autowired
    private TokenCaffineRegistryServiceImpl tokenCaffineRegistryService;


    public OauthLoginVo generatorToken(KeepUser user, String clientId,String deviceType) {
        // 1，查询用户是否之前登录过，存在tgt
        Optional<Ticket> tgtOptional = this.getTgtByUserName(user.getUsername(), deviceType);
        Ticket tgt = null;
        if(tgtOptional.isPresent()){
            tgt = tgtOptional.get();
        }else{
            tgt = this.generatorTgtToken(user,clientId,deviceType);
            this.addToken(tgt);
        }
        // 2. 创建token
        // 3. 创建refreshToken
        List<String> descendantTickets = new ArrayList<>();
        if(StringUtils.isNotBlank(tgt.getDescendantTickets())){
            descendantTickets = JSONObject.parseObject(tgt.getDescendantTickets(),List.class);
            if(CollUtil.isNotEmpty(descendantTickets)){
                descendantTickets.stream().forEach(ticketId -> deleteTicket(ticketId, TicketTypeEnum.getClassByTicketId(ticketId)));
                descendantTickets.clear();
            }
        }
        Ticket accessToken = generatorAccessToken(user,clientId,deviceType,tgt.getId());
        Ticket refreshToken = generatorRefreshToken(user,clientId,deviceType,tgt.getId());
        this.addToken(accessToken);
        this.addToken(refreshToken);
        descendantTickets.add(accessToken.getId());
        descendantTickets.add(refreshToken.getId());
        tgt.setDescendantTickets(JSONObject.toJSONString(descendantTickets));
        // 更新tgt
        this.updateToken(tgt);
        OauthLoginVo vo = new OauthLoginVo();
        vo.setAccessToken(accessToken.getId());
        vo.setRefreshToken(refreshToken.getId());
        vo.setExpiredIn(AT_TIME_TO_LIVE);
        return vo;
    }



    @Override
    public Optional<Ticket> getTgtByUserName(String username,String deviceType) {
        Optional<Ticket> tgtOptional = this.tokenRedisRegistryService.getTgtByUserName(username, deviceType);
        if(tgtOptional.isPresent()){
            return tgtOptional;
        }
        return this.tokenDbRegistryService.getTgtByUserName(username,deviceType);
    }

    @Override
    public void addToken(Ticket token) {
        this.tokenDbRegistryService.addToken(token);
        this.tokenRedisRegistryService.addToken(token);
    }


    @Override
    public void deleteTicket(String ticketId,Class clazz) {
        this.tokenDbRegistryService.deleteTicket(ticketId,clazz);
        this.tokenRedisRegistryService.deleteTicket(ticketId,clazz);
    }

    @Override
    public void updateToken(Ticket ticket) {
        this.tokenDbRegistryService.updateToken(ticket);
        this.tokenRedisRegistryService.updateToken(ticket);

    }


    private Ticket generatorAccessToken(KeepUser user, String clientId, String deviceType,String tgtId) {
        KeepAccessToken ticket = new KeepAccessToken();
        ticket.setId(AT_PREFIX + buildTickeId(AT_PREFIX));
        ticket.setUsername(user.getUsername());
        ticket.setCreateTime(LocalDateTime.now());
        ticket.setTimeToLive(AT_TIME_TO_LIVE);
        ticket.setTgtId(tgtId);
        ticket.setServiceId(clientId);
        ticket.setDeviceType(deviceType);
        return ticket;
    }

    private Ticket generatorRefreshToken(KeepUser user, String clientId, String deviceType,String tgtId) {
        KeepRefreshToken ticket = new KeepRefreshToken();
        ticket.setId(RT_PREFIX +  buildTickeId(RT_PREFIX));
        ticket.setUsername(user.getUsername());
        ticket.setCreateTime(LocalDateTime.now());
        ticket.setTimeToLive(RT_TIME_TO_LIVE);
        ticket.setTgtId(tgtId);
        ticket.setServiceId(clientId);
        ticket.setDeviceType(deviceType);
        return ticket;
    }

    private Ticket generatorTgtToken(KeepUser user, String clientId,String deviceType) {
        KeepTgtToken tgtToken = new KeepTgtToken();
        tgtToken.setId(TGT_PREFIX + buildTickeId(TGT_PREFIX));
        tgtToken.setUsername(user.getUsername());
        tgtToken.setCreateTime(LocalDateTime.now());
        tgtToken.setTimeToDie(TGT_TIME_TO_LIVE);
        tgtToken.setDescendantTickets(null);
        tgtToken.setServiceId(clientId);
        tgtToken.setDeviceType(deviceType);
        tgtToken.setDeviceType(deviceType);
        tgtToken.setLastTimeUsed(LocalDateTime.now());
        return tgtToken;
    }

    private String buildTickeId(String ticketPref){
        String ticketId = new StringBuilder(ticketPref.length())
                .append(ticketPref)
                .append('-')
                .append("V1")
                .append('-')
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .append('-')
                .append(ticketPref.length())
                .append('-')
                .append(getNextValue())
                .toString().toUpperCase();
        return EncodingUtils.encodeBase64(ticketId.getBytes());
    }

    private String getNextValue() {
        if (this.count.compareAndSet(Long.MAX_VALUE, 0)) {
            return Long.toString(Long.MAX_VALUE);
        }
        return Long.toString(this.count.getAndIncrement());
    }

    @Override
    public Ticket getTicketById(String ticketId) {
        Ticket ticket = tokenCaffineRegistryService.getTicketById(ticketId);
        if(ticket == null){
            ticket = tokenRedisRegistryService.getTicketById(ticketId);
            if(ticket == null){
                ticket = tokenDbRegistryService.getTicketById(ticketId);
                if(ticket == null || ticket.expired()){
                    throw new TokenExpection(401,"ticket is expired");
                }
            }
        }
        return ticket;
    }
}
