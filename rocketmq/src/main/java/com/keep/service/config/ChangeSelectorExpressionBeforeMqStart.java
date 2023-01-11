package com.keep.service.config;

import com.keep.service.consumer.ConsumerService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * selectorType 失效处理
 */
@Component
public class ChangeSelectorExpressionBeforeMqStart implements InitializingBean {

    @Value("${rocketmq.tag}")
    private String tags;

    @Override
    public void afterPropertiesSet() throws Exception {
        RocketMQMessageListener annoTable =
                ConsumerService.class.getAnnotation(RocketMQMessageListener.class);
        // 获取代理处理器
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(annoTable);
        // 获取私有 memberValues 属性
        Field f = invocationHandler.getClass().getDeclaredField("memberValues");
        f.setAccessible(true);
        // 获取实例的属性map
        Map<String, Object> memberValues = (Map<String, Object>)
                f.get(invocationHandler);
        // 修改属性值
        memberValues.put("selectorExpression", tags);

    }

}