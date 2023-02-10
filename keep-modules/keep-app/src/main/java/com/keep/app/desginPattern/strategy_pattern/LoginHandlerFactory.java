package com.keep.app.desginPattern.strategy_pattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
@Component
public class LoginHandlerFactory implements InitializingBean, ApplicationContextAware/*, BeanPostProcessor, BeanFactoryAware*/ {
    private static final Map<LoginType, LoginHandler<Serializable>> LOGIN_HANDLER_MAP = new EnumMap<>(LoginType.class);
    private ApplicationContext appContext;

    /**
     * 根据登录类型获取对应的处理器
     *
     * @param loginType 登录类型
     * @return 登录类型对应的处理器
     */
    public LoginHandler<Serializable> getHandler(LoginType loginType) {
        return LOGIN_HANDLER_MAP.get(loginType);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 将 Spring 容器中所有的 LoginHandler 注册到 LOGIN_HANDLER_MAP
        appContext.getBeansOfType(LoginHandler.class)
                .values()
                .forEach(handler -> LOGIN_HANDLER_MAP.put(handler.getLoginType(), handler));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

   /* @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        //log.info("setBeanFactory >>>> beanFactory >>> >>>{}",beanFactory);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //log.info("postProcessBeforeInitialization >>>> bean >>> >>>{}  beanName >>> >>>{}",bean,beanName);
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //log.info("postProcessAfterInitialization >>> >>> bean >>> >>>{}  beanName >>> >>>{}",bean,beanName);
        return null;
    }*/
}