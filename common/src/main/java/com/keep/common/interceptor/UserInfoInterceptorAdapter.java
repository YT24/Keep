package com.keep.common.interceptor;

import com.shangjian.common.core.base.bean.UserInfoHolder;
import com.shangjian.common.core.domain.dto.UserInfoDto;
import com.shangjian.common.core.utils.StringUtils;
import com.shangjian.common.core.utils.user.UserUtil;
import com.shangjian.module.common.constant.CommonBizConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lifei
 * @date 2022/6/28
 */
@Slf4j
@Aspect
@Component
public class UserInfoInterceptorAdapter {

    @Pointcut("execution(* com.shangjian.*.controller..*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        // 兼容异步线程信息拷贝
        String token = request.getHeader(CommonBizConstants.AUTHORIZATION_NAME);
        UserInfoDto userInfo = null;
        if(StringUtils.isNotBlank(token)){
            userInfo = UserUtil.getUserInfo();
            userInfo.setToken(token);
        }
        try {
            UserInfoHolder.setUserInfo(userInfo);
            return joinPoint.proceed();
        } finally {
            UserInfoHolder.remove();
        }
    }
}
