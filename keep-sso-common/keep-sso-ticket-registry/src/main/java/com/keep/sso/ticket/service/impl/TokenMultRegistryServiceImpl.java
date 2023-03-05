package com.keep.sso.ticket.service.impl;

import com.keep.sso.ticket.entity.KeepAccessToken;
import com.keep.sso.ticket.entity.KeepTgtToken;
import com.keep.sso.ticket.entity.KeepUser;
import com.keep.sso.ticket.entity.Ticket;
import com.keep.sso.ticket.service.TokenRegistryService;
import com.keep.sso.ticket.entity.vo.OauthLoginVo;
import com.keep.sso.ticket.utils.EncodingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class TokenMultRegistryServiceImpl implements TokenRegistryService {


    public static final String TGT_PREFIX = "TGT-";

    public static final String AT_PREFIX = "AT-";

    public static final String RT_PREFIX = "RT-";

    public static final Long TGT_TIME_TO_LIVE = Long.valueOf(30 * 24 * 60 * 60);

    public static final Long AT_TIME_TO_LIVE = Long.valueOf(7 * 24 * 60 * 60);

    public static final Long RT_TIME_TO_LIVE = Long.valueOf(2 * 60 * 60);

    @Autowired
    private TokenDbRegistryServiceImpl tokenDbRegistryService;
    @Autowired
    private TokenRedisRegistryServiceImpl tokenRedisRegistryService;

    @Autowired
    private TokenCaffineRegistryServiceImpl tokenCaffineRegistryService;



    @Override
    public void addToken(Ticket token) {
    }

    public OauthLoginVo generatorToken(KeepUser user, String clientId,String deviceType) {
        // 1，查询用户是否之前登录过，存在tgt
        Optional<Ticket> tgtOptional = this.tokenRedisRegistryService.getTgtByUserName(user.getUsername(), deviceType);
        if(!tgtOptional.isPresent()){
            tgtOptional = this.tokenDbRegistryService.getTgtByUserName(user.getUsername(),deviceType);
        }
        Ticket tgt = tgtOptional.get();
        if(!tgtOptional.isPresent()){
            tgt = generatorTgtToken(user,clientId,deviceType);
            this.tokenDbRegistryService.addToken(tgt);
        }
        // 2. 创建token
        Ticket accessToken = generatorAccessToken(user,clientId,deviceType,tgt.getId());

        // 3. 创建refreshToken

        OauthLoginVo vo = new OauthLoginVo();
        return vo;
    }

    private Ticket generatorAccessToken(KeepUser user, String clientId, String deviceType,String tgtId) {
        KeepAccessToken ticket = new KeepAccessToken();
        ticket.setId(AT_PREFIX + EncodingUtils.encodeBase64(user.getUsername().getBytes()).toUpperCase());
        ticket.setUsername(user.getUsername());
        ticket.setCreateTime(LocalDateTime.now());
        ticket.setTimeToDie(AT_TIME_TO_LIVE);
        ticket.setTgtId(tgtId);
        ticket.setServiceId(clientId);
        ticket.setDeviceType(deviceType);
        return null;
    }

    private Ticket generatorRefreshToken(KeepUser user, String clientId, String deviceType,String tgtId) {
        KeepAccessToken ticket = new KeepAccessToken();
        ticket.setId(RT_PREFIX + EncodingUtils.encodeBase64(user.getUsername().getBytes()).toUpperCase());
        ticket.setUsername(user.getUsername());
        ticket.setCreateTime(LocalDateTime.now());
        ticket.setTimeToDie(RT_TIME_TO_LIVE);
        ticket.setTgtId(tgtId);
        ticket.setServiceId(clientId);
        ticket.setDeviceType(deviceType);
        return null;
    }

    private Ticket generatorTgtToken(KeepUser user, String clientId,String deviceType) {
        KeepTgtToken tgtToken = new KeepTgtToken();
        tgtToken.setId(TGT_PREFIX + EncodingUtils.encodeBase64(user.getUsername().getBytes()).toUpperCase());
        tgtToken.setUsername(user.getUsername());
        tgtToken.setCreateTime(LocalDateTime.now());
        tgtToken.setTimeToDie(TGT_TIME_TO_LIVE);
        tgtToken.setDescendantTickets(null);
        tgtToken.setServiceId(clientId);
        tgtToken.setDeviceType(deviceType);
        return tgtToken;
    }

    @Override
    public Optional<Ticket> getTgtByUserName(String username,String deviceType) {

        return Optional.empty();
    }


//    @Scheduled(initialDelayString = "${sso.redisStatus.startDelay:1000}",
//            fixedDelayString = "${sso.redisStatus.repeatInterval:5000}")
//    public void redisHeartBeatCheck() {
//        try {
//            ssoRedisTemplate.opsForValue().set("flag", "true");
//            if (redisExceptionCounts > 5) {
//                setRedisHeartBeatSuccess(true);
//                ssoRedisTemplate.setRedisHeartBeatSuccess(true);
//                LOGGER.info("present environment : Redis!");
//                ssoRedisTemplate.clearExpiredRecords();
//
//                try {
//                    redisSessionHandler.switchSession();
//                } catch (IllegalAccessException illegalAccessException) {
//                    LOGGER.error("illegalAccessException", illegalAccessException);
//                } catch (InvocationTargetException invocationTargetException) {
//                    LOGGER.error("invocationTargetException", invocationTargetException);
//                } catch (InstantiationException instantiationException) {
//                    LOGGER.error("instantiationException", instantiationException);
//                }
//            }
//            redisExceptionCounts = 0;
//        } catch (RuntimeException e) {
//            redisExceptionCounts++;
//            LOGGER.info("redis exception count: {}", redisExceptionCounts);
//            if (redisExceptionCounts > 5 && redisExceptionCounts < 10) {
//                setRedisHeartBeatSuccess(false);
//                ssoRedisTemplate.setRedisHeartBeatSuccess(false);
//                LOGGER.info("present environment : NonRedis!");
//
//                try {
//                    jdbcSessionHandler.switchSession();
//                } catch (IllegalAccessException illegalAccessException) {
//                    LOGGER.error("illegalAccessException", illegalAccessException);
//                } catch (InvocationTargetException invocationTargetException) {
//                    LOGGER.error("invocationTargetException", invocationTargetException);
//                } catch (InstantiationException instantiationException) {
//                    LOGGER.error("instantiationException", instantiationException);
//                }
//            }
//        }
//    }
}
