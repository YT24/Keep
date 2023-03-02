package com.keep.app.fork.join.controller;

import com.keep.common.core.config.SpringContextHolder;
import com.keep.common.core.domain.entity.ResponseResult;
import com.spi.hello.HelloSPI;
import com.spi.hello.ImageHello;
import com.spi.hello.TextHello;
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
