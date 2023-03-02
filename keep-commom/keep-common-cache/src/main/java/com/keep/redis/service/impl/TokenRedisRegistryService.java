package com.keep.redis.service.impl;

import com.keep.redis.service.TokenRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("tokenRedisRegistryService")
public class TokenRedisRegistryService implements TokenRegistryService {

    @Autowired
    @Qualifier("tokenDbRegistryService")
    private TokenRegistryService tokenDbRegistryService;

    @Override
    public void addToken(Object token) {

    }
}
