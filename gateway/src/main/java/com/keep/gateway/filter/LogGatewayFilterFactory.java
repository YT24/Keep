package com.keep.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
public class LogGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {


    @Override
    public GatewayFilter apply(NameValueConfig config) {

        return (exchange,chain) -> {
            ServerHttpRequest request = exchange.getRequest().mutate().build();
            ServerWebExchange webExchange = exchange.mutate().request(request).build();
            log.info("配置参数：{},{}",config.getName(),config.getValue());
            log.info("请求路径：{}，请求模式：{}",request.getPath(),request.getMethod());
            return chain.filter(webExchange);
        };
    }
}
