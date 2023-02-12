package com.keep.common.core.config;

import com.keep.common.core.interceptor.IdempotentInteceptor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@AllArgsConstructor
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {


    @Autowired
    private IdempotentInteceptor idempotentInteceptor;
//
//    @Autowired
//    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> excludePathList = new ArrayList<>();
        excludePathList.add("/swagger**/**");
        excludePathList.add("/webjars/**");
        excludePathList.add("/v3/**");
        excludePathList.add("/doc.html");
        excludePathList.add("/error");
        excludePathList.add("/api/v1/user/login");

        // 接口幂等
//        registry.addInterceptor(idempotentInteceptor).addPathPatterns("/**")
//                .excludePathPatterns(excludePathList);

        // Token
        //registry.addInterceptor(loginInterceptor).addPathPatterns("/**");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT","PATCH")
                .maxAge(36000);
    }
}

 