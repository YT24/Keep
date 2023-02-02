package com.keep.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = {"com.keep","com.keep.common"})
//@NacosPropertySource(dataId = "keep-app",autoRefreshed = true)
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class,args);
    }
}
