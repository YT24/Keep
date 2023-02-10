package com.keep.app.controller;

import com.keep.common.domain.entity.ResponseResult;
import com.keep.common.fegin.UserInfoClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.VM;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/api/v1")
public class TestController {



//    @Value("${keep.app.test}")
    private String k1;

    @Autowired
    private UserInfoClient userInfoClient;

    @GetMapping("get")
    public ResponseResult get() {
        return ResponseResult.success(k1);
    }

    @SneakyThrows
    @GetMapping("userInfo")
    public ResponseResult userInfo(@RequestHeader("Authorization") String token){
        //Thread.sleep(6000);

        return ResponseResult.success(userInfoClient.getUserInfo(token));
    }

    /**
     * 无法创建本地线程
     */
    @RequestMapping("oom/unableToCreateNativeThread")
    public void unableToCreateNativeThread(){
        for (int i = 0; ; i++) {
            log.info(">> >> >> >> >> >> >> >>  {}",i);
            new Thread(() ->{
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


    /**
     * 直接内存溢出
     * 默认的maxDirectmemory 是：3607.5 mb
     * @return
     */
    @RequestMapping("oom/direct_memory")
    public ResponseResult direct_memory(){
        double v = VM.maxDirectMemory() / (double) 1024 / 1024;
        System.out.println("默认的maxDirectmemory 是："+v+" mb");

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(513 * 1024 * 1024);

        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseResult.success();
    }

    @RequestMapping("oom/gcOverHead")
    public void gcOverHead(){
        List<String> strList = new ArrayList<>();
        int count = 1000000;
        String str1 = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        for(int i=0; i< count; i++) {
            strList.add(str1 + "i" + i);
        }
        System.out.println("...");

        for(int i=0; i< count; i++) {
            strList.add(str1 + "i" + i);
            strList.add(str1 + "i" + i);
            strList.add(str1 + "i" + i);
        }
        System.out.println("done");

        Collections.synchronizedList(strList);
    }

}
