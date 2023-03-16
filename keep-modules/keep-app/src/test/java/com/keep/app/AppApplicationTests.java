package com.keep.app;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class AppApplicationTests {

    @Autowired
    private Redisson redisson;

    @Test
    void test(){
        RLock aaa = redisson.getLock("AAA");
        aaa.lock();
    }
}
