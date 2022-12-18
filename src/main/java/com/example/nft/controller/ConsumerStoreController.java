package com.example.nft.controller;


import com.example.nft.controller.param.ConsumerStoreParam;
import com.example.nft.entity.Consumer;
import com.example.nft.entity.Store;
import com.example.nft.service.ConsumerStoreService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/consumerStore")
public class ConsumerStoreController {


    @Resource
    private ConsumerStoreService consumerStoreService;


    // 店铺顾客建立联系接口
    @PostMapping("/add")
    public Result consumerAndStore(@RequestBody ConsumerStoreParam consumerStoreParam){
        System.out.println(consumerStoreParam.getConsumerUuid() + consumerStoreParam.getStoreUuid());
        String result = consumerStoreService.ConsumerAndStore(consumerStoreParam.getConsumerUuid(),consumerStoreParam.getStoreUuid());
        System.out.println(result);
        return ResultGenerator.genSuccessResult(result);
    }

    // 店铺顾客联系删除
    @PostMapping("/remove")
    public Result removeConsumerStore(@RequestBody ConsumerStoreParam consumerStoreParam){
        String result = consumerStoreService.removeConsumerStore(consumerStoreParam.getConsumerUuid(),consumerStoreParam.getStoreUuid());
        System.out.println(result);
        return ResultGenerator.genSuccessResult(result);
    }

    // 查询用户
    @PostMapping("/findConsumerByStoreId")
    public Result findConsumerByStoreId(@RequestBody ConsumerStoreParam consumerStoreParam){
        List<Consumer> result = consumerStoreService.findConsumerByStoreUuid(consumerStoreParam.getStoreUuid(), consumerStoreParam.getCurrentPage());
        System.out.println(result);
        return ResultGenerator.genSuccessResult(result);
    }



    // 查询顾客
    @PostMapping("/findStoreByConsumerId")
    public Result findStoreByConsumerId(@RequestBody ConsumerStoreParam consumerStoreParam){
        List<Store> result = consumerStoreService.findStoreByConsumerUuid(consumerStoreParam.getConsumerUuid());
        return ResultGenerator.genSuccessResult(result);
    }
}
