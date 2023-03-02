package com.example.nft.controller;

import com.example.nft.controller.param.BalanceParam;
import com.example.nft.controller.param.PageParam;
import com.example.nft.dao.ConsumerMapper;
import com.example.nft.entity.ConsumerBalance;
import com.example.nft.service.ConsumerBalanceService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/balance")
public class ConsumerBalanceController extends BaseController{

    @Resource
    private ConsumerBalanceService consumerBalanceService;

    @Resource
    private ConsumerMapper consumerMapper;

    @PostMapping("/money/change")
    public Result moneyChange(@RequestBody BalanceParam balanceParam){
        ConsumerBalance result = consumerBalanceService.moneyChange(balanceParam.getBalanceOwnerPhone(), balanceParam.getStoreUuid(), balanceParam.getCostMoney());

        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/service/change")
    public Result serviceChange(@RequestBody BalanceParam balanceParam){
        System.out.println(balanceParam.getBalanceOwnerPhone());
        ConsumerBalance result = consumerBalanceService.serviceChange(balanceParam.getBalanceOwnerPhone(), balanceParam.getStoreUuid(), balanceParam.getCostService1(), balanceParam.getCostService2(),
                balanceParam.getCostService3(),balanceParam.getCostService4(),balanceParam.getCostService5());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/findOne")
    public Result findOne(@RequestBody BalanceParam balanceParam){
        ArrayList<Object> result = consumerBalanceService.findOne(balanceParam.getBalanceOwnerPhone(), balanceParam.getStoreUuid());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/findMany")
    public Result findMany(@RequestBody PageParam pageParam){
        HashMap<Object, Object> result = consumerBalanceService.findMany(pageParam.getStoreUuid(), pageParam.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }


    @PostMapping("/change")
    public Result change(@RequestBody BalanceParam balanceParam){
        String result;
        if(balanceParam.getConsumerUuid() != null){
            // uuid转为手机号
            String phone = consumerMapper.selectByUuid(balanceParam.getConsumerUuid()).getConsumerPhone();
            result = consumerBalanceService.change(phone, balanceParam.getStoreUuid() ,balanceParam.getCostMoney(), balanceParam.getCostService1(), balanceParam.getCostService2(),
                    balanceParam.getCostService3(),balanceParam.getCostService4(),balanceParam.getCostService5());
            return ResultGenerator.genSuccessResult(result);
        }

        result = consumerBalanceService.change(balanceParam.getBalanceOwnerPhone(), balanceParam.getStoreUuid() ,balanceParam.getCostMoney(), balanceParam.getCostService1(), balanceParam.getCostService2(),
                balanceParam.getCostService3(),balanceParam.getCostService4(),balanceParam.getCostService5());
        return ResultGenerator.genSuccessResult(result);
    }

}
