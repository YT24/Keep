package com.spi.cache;

public class RedisCacheSpi implements CacheSpi{
    @Override
    public void cache() {
        System.out.println("I am redis !!!");
    }
}
