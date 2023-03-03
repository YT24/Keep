package keep.sso.ticket.service.impl;

import keep.sso.ticket.entity.Ticket;
import keep.sso.ticket.service.TokenRegistryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
@Slf4j
@Service("tokenMultRegistryService")
public class TokenMultRegistryService implements TokenRegistryService {

    @Autowired
    @Qualifier("tokenDbRegistryService")
    private TokenRegistryService tokenDbRegistryService;

    @Autowired
    @Qualifier("tokenRedisRegistryService")
    private TokenRedisRegistryService tokenRedisRegistryService;

    @Autowired
    @Qualifier("tokenJvmRegistryService")
    private TokenCaffineRegistryService tokenJvmRegistryService;




    @Override
    public void addToken(Ticket token) {
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
