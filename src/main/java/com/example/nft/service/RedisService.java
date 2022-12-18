package com.example.nft.service;

public interface RedisService {
    void set(String key, String value);
    String get(String key);

    //设置超时时间
    boolean expire(String key, long expire);
    void remove(String key);
    //自增操作
    Long increment(String key, long delta);
}
