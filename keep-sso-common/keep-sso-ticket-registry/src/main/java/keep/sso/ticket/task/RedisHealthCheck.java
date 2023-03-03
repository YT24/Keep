package keep.sso.ticket.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Objects;

@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class RedisHealthCheck {

    private boolean redisHeartBeatSuccess =  Boolean.TRUE;

    public static final String PONG = "PONG";

    private volatile Integer redisExceptionCounts = 0;

    @Autowired
    private RedisTemplate redisTemplate;


    @Scheduled(initialDelayString = "${sso.redisStatus.startDelay:1000}",
            fixedDelayString = "${sso.redisStatus.repeatInterval:5000}")
    public void checkRedisHeath() {
        try {
            redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.ping();
                }
            });
            redisExceptionCounts = 0;

        } catch (Exception e) {
            redisExceptionCounts++;
            if(redisExceptionCounts > 6){
                redisHeartBeatSuccess = Boolean.FALSE;
                log.error("present environment : NonRedis!!!");
            }

        }

    }
}
