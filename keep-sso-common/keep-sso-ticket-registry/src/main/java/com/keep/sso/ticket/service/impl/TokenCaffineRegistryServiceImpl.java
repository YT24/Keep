package com.keep.sso.ticket.service.impl;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.keep.sso.ticket.service.TokenRegistryService;
import com.keep.sso.ticket.entity.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TokenCaffineRegistryServiceImpl implements TokenRegistryService {

    private static final int INITIAL_CACHE_SIZE = 50;

    private final Map<String, Ticket> mapInstance;

    private final LoadingCache<String, Ticket> storage;


    public TokenCaffineRegistryServiceImpl() {
        this.storage = Caffeine.newBuilder()
                .initialCapacity(INITIAL_CACHE_SIZE)
                .maximumSize(INITIAL_CACHE_SIZE)
                // TODO 设置本地缓存过期时间
                .expireAfterWrite(6000, TimeUnit.SECONDS)
                //.expireAfter()
                .build(s -> {
                    log.error("Load operation of the cache is not supported.");
                    return null;
                });
        this.mapInstance = this.storage.asMap();

    }

    public Map<String, Ticket> getMapInstance() {
        return mapInstance;
    }

    @Override
    public void addToken(Ticket token) {

    }

    @Override
    public Optional<Ticket> getTgtByUserName(String username,String deviceType) {
        return Optional.empty();
    }
}
