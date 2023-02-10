package com.keep.common.advice;

import com.alibaba.fastjson.JSONObject;
import com.keep.common.domain.entity.UserVoHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Slf4j
@Component
@Aspect
//@Order(1)
public class ReqLogAdvice {

    //使用@Pointcut注解声明频繁使用的切入点表达式
    @Pointcut("execution(* com.keep.*.controller..*.*(..))")
    public void performance() {
    }

    @Before("performance()")
    public void deBefore(JoinPoint joinPoint) {
        //log.info("前置通知");
    }

    @AfterReturning(returning = "ret", pointcut = "performance()")
    public void doAfterReturning(Object ret) {
        //log.info("后置返回通知");
    }

    @AfterThrowing("performance()")
    public void throwss(JoinPoint jp){
        //log.info("后置异常通知");
    }

    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("performance()")
    public void after(JoinPoint jp){
        //log.info("后置最终通知");
    }

    @Around("performance()")
    public Object arround(ProceedingJoinPoint joinPoint) throws Throwable {
        //log.info("环绕通知");
        Object proceed = null;
        // 日志记录参数获取
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> declaringClass = method.getDeclaringClass();
        String className = declaringClass.getName();
        String methodName = method.getName();
        String requestUri= req.getRequestURI();
        String classMethodName = className+"."+methodName;
        String appName = "BServer";
        String queryString = req.getQueryString();
        String pArgsJsonStr = null ;
        try {
            log.info("####请求开始,App-Name：{},Request-Url:{} {},Request-Param:{},Request-Body:{}",appName,requestUri,classMethodName,queryString,pArgsJsonStr);
            proceed = joinPoint.proceed();
            String resJson = JSONObject.toJSONString(proceed);
            log.info("####请求结束,App-Name：{},Request-Url:{} {},Request-Param:{},Response-Body:{}",appName,requestUri,classMethodName,pArgsJsonStr,resJson);
        } finally {
            UserVoHolder.remove();
        }
        return proceed;
    }
}
