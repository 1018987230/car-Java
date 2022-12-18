package com.example.nft.Handler;

import com.example.nft.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ListenHandler {

    @Autowired
    private RedisUtil redisUtil;

    public ListenHandler(){
        System.out.println("开始初始化");
    }

    @PostConstruct
    public void init() {
        System.out.println("Redis及数据库开始初始化");
        Integer nowCount = 0;
//        if (personCountService.insertCountDay(nowCount) == 0){
//            redisUtil.set("pageA_count", 0);
//            System.out.println("Redis及数据库初始化完毕");
//        }
    }
}


