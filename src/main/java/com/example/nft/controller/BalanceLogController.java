package com.example.nft.controller;


import com.example.nft.controller.param.BalanceLogParam;
import com.example.nft.dao.BalanceLogMapper;
import com.example.nft.entity.BalanceLog;
import com.example.nft.service.BalanceLogService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/balance/log")
public class BalanceLogController extends BaseController {

    @Resource
    private BalanceLogService balanceLogService;

    @Resource
    private BalanceLogMapper balanceLogMapper;

    @PostMapping("/find")
    public Result find(@RequestBody BalanceLogParam balanceLogParam){

        HashMap<Object, Object> result =  balanceLogService.findMany(balanceLogParam.getBalanceLogPhone(), balanceLogParam.getBalanceLogStore(), balanceLogParam.getStartTime(),
                balanceLogParam.getEndTime(), balanceLogParam.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }


    /**
     * @param balanceLogParam
     * @return
     */
    @PostMapping("/dayAddSum")
    public Result dayAddSum(@RequestBody BalanceLogParam balanceLogParam){
        Map result = balanceLogService.findDayAddSum(balanceLogParam.getBalanceLogStore(), balanceLogParam.getStartTime(),balanceLogParam.getEndTime());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/dayReduceSum")
    public Result dayReduceSum(@RequestBody BalanceLogParam balanceLogParam){
        Map result = balanceLogService.findDayReduceSum(balanceLogParam.getBalanceLogStore(), balanceLogParam.getStartTime(),balanceLogParam.getEndTime());
        return ResultGenerator.genSuccessResult(result);
    }


    @PostMapping("/dayServiceAddSum")
    public Result dayServiceAddSum(@RequestBody BalanceLogParam balanceLogParam){
        Map result = balanceLogService.findServiceDayAddSum( balanceLogParam.getBalanceLogStore(),balanceLogParam.getStartTime(),balanceLogParam.getEndTime());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/dayServiceReduceSum")
    public Result dayServiceReduceSum(@RequestBody BalanceLogParam balanceLogParam){
        Map result = balanceLogService.findServiceDayReduceSum(balanceLogParam.getBalanceLogStore(), balanceLogParam.getStartTime(),balanceLogParam.getEndTime());
        return ResultGenerator.genSuccessResult(result);
    }

}
