package com.spi.cache;

public class JvmCacheSpi implements CacheSpi{
    @Override
    public void cache() {
        System.out.println("I am jvm !!!");
    }
}
