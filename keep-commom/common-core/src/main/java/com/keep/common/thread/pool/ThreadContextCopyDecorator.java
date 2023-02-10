package com.keep.common.thread.pool;

import org.springframework.core.task.TaskDecorator;

/**
 * @author lifei
 * @date 2022/10/27
 */
public class ThreadContextCopyDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        UserInfo userInfo = UserInfoHolder.getUserInfo();
//        return () -> {
//            try {
//                UserInfoHolder.setUserInfo(userInfo);
//                RequestContextHolder.setRequestAttributes(requestAttributes);
//                runnable.run();
//            }finally {
//                RequestContextHolder.resetRequestAttributes();
//                UserInfoHolder.remove();
//            }
//
//        };
        return null;
    }
}
