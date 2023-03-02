package com.example.nft.controller;

import com.example.nft.controller.param.PageParam;
import com.example.nft.dao.ConsumeLogMapper;
import com.example.nft.entity.ConsumeLog;
import com.example.nft.service.ConsumeLogService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2023/2/21 下午2:25
 * @PackageName:com.example.nft.controller
 * @ClassName: ConsumeLogController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/consumeLog")
public class ConsumeLogController {

    @Resource
    private ConsumeLogService consumeLogService;


    @PostMapping("/add")
    public Result add(@RequestBody ConsumeLog consumeLog){

        String result = consumeLogService.add(consumeLog);
        return ResultGenerator.genSuccessResult("");
    }

    @PostMapping("/findMany")
    public Result findMany(@RequestBody PageParam pageParam){

        HashMap<String, Object> result =  consumeLogService.findMany(pageParam.getConsumerUuid(), pageParam.getStoreUuid(), pageParam.getCurrentPage());
        System.out.println(result);

        return ResultGenerator.genSuccessResult(result);
    }
}
