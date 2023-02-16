package com.example.nft.controller;


import com.example.nft.controller.param.ConsumerStoreParam;
import com.example.nft.entity.Consumer;
import com.example.nft.entity.ConsumerBalance;
import com.example.nft.entity.Store;
import com.example.nft.service.ConsumerBalanceService;
import com.example.nft.service.ConsumerStoreService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/consumerStore")
@Api(tags = "3.店铺顾客联合操作模块")
public class ConsumerStoreController {


    @Resource
    private ConsumerStoreService consumerStoreService;

    @Resource
    private ConsumerBalanceService consumerBalanceService;

    private static final Integer NORMAL_STATUS = 0;

    private static final Integer ABNORMAL_STATUS = 1;




    // 店铺顾客建立联系接口
    @PostMapping("/add")
    @ApiOperation(value = "店铺顾客建立联系")
    public Result consumerAndStore(@RequestBody ConsumerStoreParam consumerStoreParam){

        String result = consumerStoreService.ConsumerAndStore(consumerStoreParam.getConsumerUuid(),consumerStoreParam.getStoreUuid());
        // 将店铺中用户的账户状态改变，初次建立联系默认为‘0’，多一步状态置为0操作但是无影响，若之前有建立且状态已为1，置为0，成功
        if(!consumerBalanceService.statusChange(consumerStoreParam.getConsumerUuid(),consumerStoreParam.getStoreUuid(), NORMAL_STATUS).equals("success")){
            return ResultGenerator.genFailResult("账户状态改变失败！");
        }

        return ResultGenerator.genSuccessResult(result);
    }

    // 店铺顾客联系删除
    @PostMapping("/remove")
    @ApiOperation(value = "店铺用户断开联系")
    public Result removeConsumerStore(@RequestBody ConsumerStoreParam consumerStoreParam){
        String result = consumerStoreService.removeConsumerStore(consumerStoreParam.getConsumerUuid(),consumerStoreParam.getStoreUuid());

        // 更改用户账户的状态变为1
        if(!consumerBalanceService.statusChange(consumerStoreParam.getConsumerUuid(),consumerStoreParam.getStoreUuid(), ABNORMAL_STATUS).equals("success")){
            return ResultGenerator.genFailResult("账户状态改变失败！");
        }
        return ResultGenerator.genSuccessResult(result);
    }

    // 查询用户
    @PostMapping("/findConsumerByStoreId")
    @ApiOperation(value="查询店铺下的所有用户")
    public Result findConsumerByStoreId(@RequestBody ConsumerStoreParam consumerStoreParam){
        HashMap<String, Object> result = consumerStoreService.findConsumerByStoreUuid(consumerStoreParam.getStoreUuid(), consumerStoreParam.getCurrentPage());

        return ResultGenerator.genSuccessResult(result);
    }



    // 查询顾客
    @PostMapping("/findStoreByConsumerId")
    @ApiOperation(value="查询顾客加入的所有店铺")
    public Result findStoreByConsumerId(@RequestBody ConsumerStoreParam consumerStoreParam){
        HashMap<String, Object> result = consumerStoreService.findStoreByConsumerUuid(consumerStoreParam.getConsumerUuid());
        return ResultGenerator.genSuccessResult(result);
    }
}
