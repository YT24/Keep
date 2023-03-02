package com.keep.redis.service;

public interface TokenRegistryService<T>{

    <S extends T> void addToken(S token);
}
