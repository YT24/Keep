package com.keep.sso;

import com.keep.sso.ticket.entity.KeepTgtToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SsoApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void test(){
        KeepTgtToken tgt = new KeepTgtToken();
        tgt.setUsername("1qazxsw2");
        tgt.setDescendantTickets("AT-werswde");
        redisTemplate.boundValueOps("TGT-swdfed").set(tgt,60, TimeUnit.MINUTES);
        Object o = redisTemplate.boundValueOps("TGT-swdfed").get();
        System.out.println(o.toString());

        redisTemplate.opsForSet().getOperations().boundValueOps("1qazxsw2:deviceType").set(Arrays.asList("wqerfc:apple"));

        Object members = redisTemplate.opsForSet().getOperations().boundValueOps("1qazxsw2:deviceType").get();
        System.out.println(members.toString());
    }

}
