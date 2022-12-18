package com.example.nft.controller;


import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.controller.param.ConsumerParam;
import com.example.nft.controller.param.LoginParam;
import com.example.nft.controller.param.PageParam;
import com.example.nft.controller.param.PasswordParam;
import com.example.nft.entity.Consumer;
import com.example.nft.service.ConsumerService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/consumer")
public class ConsumerController extends BaseController {

    @Autowired
    private ConsumerService consumerService;


    @PostMapping("/login")
    public Result login(@RequestBody LoginParam loginParam){
        String result = consumerService.login(loginParam.getAccount(), loginParam.getPassword());

        return ResultGenerator.genSuccessResult(result);
    }


    @PostMapping("/add")
    public Result add(@RequestBody RegisterParam registerParam){
        String result = consumerService.add(registerParam.getAccount(),registerParam.getPassword(),registerParam.getValid());
        if(!result.equals("success")){
            return ResultGenerator.genFailResult(500, result);
        }
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/status/change")
    public Result statusChange(@RequestBody ConsumerParam consumerParam){
        String result = consumerService.statusChange(consumerParam.getConsumerStatus() ,consumerParam.getConsumerPhone());
        if(!result.equals("success")){
            return ResultGenerator.genFailResult(500, result);
        }
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/change")
    public  Result change(@RequestBody Consumer consumer){

        String result = consumerService.change(consumer);
        if(!result.equals("success")){
            return ResultGenerator.genFailResult(500, result);
        }
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/password/change")
    public Result passwordChange(@RequestBody PasswordParam passwordParam){
        System.out.println(passwordParam);
        String result = consumerService.passwordChange(passwordParam.getConsumerPhone(), passwordParam.getOldPassword(),
                passwordParam.getNewPassword1(),passwordParam.getNewPassword2());

        return ResultGenerator.genSuccessResult(result);

    }

    @PostMapping("/findMany")
    public Result findMany(@RequestBody PageParam param){
        List<Consumer> result = consumerService.findMany(param.getCurrentPage());

        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 查询禁用人员信息
     * @param param
     * @return
     */
    @PostMapping("/findBanMany")
    public Result findBanMany(@RequestBody PageParam param){
        List<Consumer> result = consumerService.findBanMany(param.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/count")
    public Result count(){
        int result = consumerService.count();
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/findByPhone")
    public Result findByPhone(@RequestBody ConsumerParam consumerParam){
        Consumer result = consumerService.findByPhone(consumerParam.getConsumerPhone());
        if(result == null){
            return ResultGenerator.genSuccessResult(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/findByNumber")
    public Result findByNumber(@RequestBody ConsumerParam consumerParam){
        Consumer result = consumerService.findByNumber(consumerParam.getCarNumber());
        if(result == null){
            return ResultGenerator.genSuccessResult(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/findCarAndStore")
    public Result findCarAndStore(@RequestBody ConsumerParam consumerParam){
        HashMap<String, Object> result = consumerService.findCarAndStore(consumerParam.getConsumerPhone());

        return ResultGenerator.genSuccessResult(result);
    }
}















