package com.keep.common.advice;

import com.alibaba.fastjson.JSONObject;
import com.keep.common.entity.UserToHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Slf4j
@Component
@Aspect
@Order(1)
public class ReqLogAdvice {

    //使用@Pointcut注解声明频繁使用的切入点表达式
    @Pointcut("execution(* com.keep.*.controller..*.*(..))")
    public void performance() {
    }

    @Around("execution(* com.keep.*.controller..*.*(..))")
    public Object arround(ProceedingJoinPoint joinPoint) throws Throwable {
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
        Object[] args = joinPoint.getArgs();
        /*List<Object> pArgs = Lists.newArrayList(args).stream()
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());*/
        String pArgsJsonStr = null /*JSONObject.toJSONString(pArgs)*/;
        try {
            log.info("####请求开始,App-Name：{},Request-Url:{} {},Request-Param:{},Request-Body:{}",appName,requestUri,classMethodName,queryString,pArgsJsonStr);
            proceed = joinPoint.proceed();
            String resJson = JSONObject.toJSONString(proceed);
            log.info("####请求结束,App-Name：{},Request-Url:{} {},Request-Param:{},Response-Body:{}",appName,requestUri,classMethodName,pArgsJsonStr,resJson);

        } finally {
            UserToHolder.remove();
        }
        return proceed;
    }
}
