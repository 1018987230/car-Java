package com.example.nft.controller;

import com.example.nft.commons.AgentThreadLocal;
import com.example.nft.controller.param.OperatorParam;
import com.example.nft.entity.Operator;
import com.example.nft.service.OperatorService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2022/11/27 下午11:03
 * @PackageName:com.example.nft.controller
 * @ClassName: OperatorController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/operator")
public class OperatorController extends BaseController{

    @Resource
    private OperatorService operatorService;

    @PostMapping("/add")
    public Result add(@RequestBody Operator operator){
        System.out.println(operator);

        String result = operatorService.add(operator);

        return ResultGenerator.genSuccessResult(result);
    }


    @PostMapping("/login")
    public Result login(@RequestBody OperatorParam operatorParam){
        HashMap<String, Object> result = operatorService.login(operatorParam.getOperatorBelong(),operatorParam.getOperatorPhone(), operatorParam.getOperatorPassword());

        return ResultGenerator.genSuccessResult(result);
    }


    @GetMapping("/findMany/{storeUuid}")
    public Result findMany(@PathVariable("storeUuid") String storeUuid){

        List<Operator> result = operatorService.findMany(storeUuid);

        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 改变操作员的status
     * @param operatorParam
     * @return
     */
    @PostMapping("/statusChange")
    public Result statusChange(@RequestBody OperatorParam operatorParam){

        System.out.println("+++" + AgentThreadLocal.get());
        String result = operatorService.changeStatus(operatorParam.getOperatorBelong(),operatorParam.getOperatorPhone(), operatorParam.getOperatorStatus());
        return ResultGenerator.genSuccessResult(result);
    }


    /**
     * 修改密码
     * @param operatorParam
     * @return
     */
    @PostMapping("/passwordChange")
    public Result passwordChange(@RequestBody OperatorParam operatorParam){
        String result = operatorService.changePassword(operatorParam.getOperatorBelong(),operatorParam.getOperatorPhone(), operatorParam.getOperatorPassword(), operatorParam.getNewPassword());
        return ResultGenerator.genSuccessResult(result);
    }
}
