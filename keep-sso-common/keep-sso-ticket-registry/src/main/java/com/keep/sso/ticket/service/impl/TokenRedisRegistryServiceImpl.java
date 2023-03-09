package com.keep.sso.ticket.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.keep.sso.ticket.entity.KeepAccessToken;
import com.keep.sso.ticket.entity.KeepTgtToken;
import com.keep.sso.ticket.entity.Ticket;
import com.keep.sso.ticket.enums.TicketTypeEnum;
import com.keep.sso.ticket.service.TokenRegistryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TokenRedisRegistryServiceImpl implements TokenRegistryService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void addToken(Ticket token) {
        if (token instanceof KeepTgtToken) {
            redisTemplate.opsForSet().add(token.getUsername(), token.getId() + ":" + token.getDeviceType());
        }
        redisTemplate.boundValueOps(token.getId()).set(JSONObject.toJSONString(token),token.getTimeToLive(), TimeUnit.SECONDS);
    }

    @Override
    public Optional<Ticket> getTgtByUserName(String username, String deviceType) {
        Object keys = redisTemplate.opsForSet().members(username);
        final Set<String> tgtDeviceKeys = (Set<String>) keys;
        for (String tgtDeviceKey : tgtDeviceKeys) {
            String[] strArray = tgtDeviceKey.split(":");
            String tgtRedisKey = strArray[0];
            String tgtDeviceType = strArray[1];
            if (!Objects.equals(deviceType, tgtDeviceType)) {
                continue;
            }
            Object tgtObj = this.redisTemplate.boundValueOps(tgtRedisKey).get();
            if(tgtObj == null){
                return Optional.empty();
            }
            return Optional.ofNullable(JSONObject.parseObject(tgtObj.toString(), KeepTgtToken.class));
        }

        return Optional.empty();
    }

    @Override
    public void deleteTicket(String ticketId, Class clazz) {
        redisTemplate.delete(ticketId);
    }

    @Override
    public void updateToken(Ticket ticket) {
        redisTemplate.boundValueOps(ticket.getId()).set(JSONObject.toJSONString(ticket),ticket.getTimeToLive(),TimeUnit.SECONDS);
    }

    @Override
    public Ticket getTicketById(String ticketId) {
        Object ticketObj = this.redisTemplate.boundValueOps(ticketId).get();
        if(ticketObj == null){
            return null;
        }
        Class<Ticket> classByTicketId = TicketTypeEnum.getClassByTicketId(ticketId);
        return ticketObj == null ? null : JSONObject.parseObject(ticketObj.toString(), classByTicketId);
    }
}
