package com.keep.gateway.filter;


import com.keep.gateway.constants.GlobalConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class LogFilter implements GlobalFilter, Ordered {


    private static final String START_TIME = "startTime";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        try {
            exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                Long startTime = exchange.getAttribute(START_TIME);
                if (startTime != null) {
                    Long executeTime = (System.currentTimeMillis() - startTime);
                    MDC.put("host_name", exchange.getRequest().getRemoteAddress().getHostString());
                    MDC.put("api_url", exchange.getRequest().getURI().getRawPath());
                    MDC.put("cost_time", String.valueOf((executeTime / 1000.00)));
                    MDC.put("response_code",exchange.getAttribute(GlobalConstants.RESP_CODE));
                    MDC.put("response_msg",exchange.getAttribute(GlobalConstants.RESP_MSG));
                }
                log.info("success");
            }));
        } finally {
            MDC.clear();
        }
    }

    /**
     * order值,order值越小,优先级越高,执行顺序越靠前。
     * @return
     */
    @Override
    public int getOrder() {
        return -1;
    }
}