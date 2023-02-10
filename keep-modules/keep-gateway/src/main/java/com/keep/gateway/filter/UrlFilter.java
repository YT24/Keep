package com.keep.gateway.filter;


import com.keep.gateway.properties.AllowPathProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class UrlFilter implements GlobalFilter, Ordered {


    @Autowired
    private AllowPathProperties allowPathProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(
                () -> {
                    log.info("UrlFilter - 拦截");
                    log.info("path-list:"+allowPathProperties.getPaths());
                }
        ));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}