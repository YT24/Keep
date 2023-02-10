package com.keep.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@Configuration
public class RequestRateLimiterConfig {

	/**
	 * 按URL限流,即以每秒内请求数按URL分组统计，超出限流的url请求都将返回429状态
	 */
    @Bean
    @Primary
    KeyResolver apiKeyResolver() {
		//按URL限流
		return exchange -> Mono.just(exchange.getRequest().getPath().toString());
	}

	/**
	 * 这里根据用户ID限流，请求路径中必须携带userId参数
	 */
    @Bean
    KeyResolver userKeyResolver() {
        //按用户限流
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
    }

	/**
	 * 通过exchange对象可以获取到请求信息，这边用了HostName
	 */
    @Bean
    KeyResolver ipKeyResolver() {
        //按IP来限流
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

}