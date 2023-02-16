package com.keep.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class LogGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {


    @Override
    public GatewayFilter apply(NameValueConfig config) {

        return (exchange,chain) -> {
            ServerHttpRequest request = exchange.getRequest().mutate().build();
            ServerWebExchange webExchange = exchange.mutate().request(request).build();
            return chain.filter(webExchange);
        };
    }
}
