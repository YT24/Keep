package com.keep.app.controller;

import com.keep.common.entity.ResponseResult;
import com.keep.common.expection.CustomExpection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.VM;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class TestController {


    @Value("${k1}")
    private String k1;

    @GetMapping("get")
    public ResponseResult get() {
//        if (true) {
//            throw new CustomExpection("12345");
//        }
        return ResponseResult.success();

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
