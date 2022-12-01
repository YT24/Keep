package com.keep.app.advice;

import com.keep.app.annotation.AutoIdempotent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class AutoIdempotentAdvice {


    //使用@Pointcut注解声明频繁使用的切入点表达式
    @Pointcut("@annotation(com.keep.app.annotation.AutoIdempotent)")
    public void pointCut() {
    }

    @Before("pointCut() && @annotation(autoIdempotent)")
    public void arround(JoinPoint joinPoint, AutoIdempotent autoIdempotent) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        tokenService.checkToken(request.getHeader("token"));
    }
}
