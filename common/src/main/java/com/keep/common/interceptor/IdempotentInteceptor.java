package com.keep.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class IdempotentInteceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
//        // 接口幂等校验 每次接口访问需要带一个token
//        Method method = ((HandlerMethod) handler).getMethod();
//        AutoIdempotent idempotent = method.getAnnotation(AutoIdempotent.class);
//        if (Objects.nonNull(idempotent)) {
//            try {
//                return tokenService.checkToken(request.getHeader("token"));
//            } catch (AutoIdempotentExpection e) {
//                throw e;
//            }
//
//        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
    }

}