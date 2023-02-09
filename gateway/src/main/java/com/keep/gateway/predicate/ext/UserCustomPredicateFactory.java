package com.keep.gateway.predicate.ext;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

public class UserCustomPredicateFactory extends AbstractRoutePredicateFactory {



    public UserCustomPredicateFactory(){
        super(UserCustomPredicateConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Object config) {

        return serverWebExchange -> {
            return Boolean.parseBoolean(null);
        };
    }
}
