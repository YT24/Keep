package com.keep.redis.service.impl;

import com.keep.redis.service.TokenRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("tokenJvmRegistryService")
public class TokenCaffineRegistryService implements TokenRegistryService {


    @Autowired
    @Qualifier("tokenRedisRegistryService")
    private TokenRedisRegistryService tokenJvmRegistryService;


    @Override
    public void addToken(Object token) {

    }
}
