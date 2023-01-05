package com.keep.app.controller;

import com.keep.common.advice.RLock;
import com.keep.common.config.SpringContextHolder;
import com.keep.common.entity.ResponseResult;
import com.keep.common.enums.IdTypeEnum;
import com.spi.hello.ImageHello;
import com.spi.hello.TextHello;
import com.spi.hello.HelloSPI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ServiceLoader;


@RestController
@RequestMapping(value = "/api/v1")
public class SpiController {




    //@RLock(name = IdTypeEnum.INDICATOR_APPROVAL)
    @GetMapping("spi")
    public ResponseResult spi(){
        ServiceLoader<HelloSPI> serviceLoader = ServiceLoader.load(HelloSPI.class);
        for (HelloSPI helloSPI : serviceLoader) {
            helloSPI.sayHello();
        }

        SpringContextHolder.getBean(ImageHello.class).sayHello();
        SpringContextHolder.getBean(TextHello.class).sayHello();


        return ResponseResult.success();
    }
}
