package com.example.nft.controller;


import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.controller.param.ConsumerParam;
import com.example.nft.controller.param.LoginParam;
import com.example.nft.controller.param.PageParam;
import com.example.nft.controller.param.PasswordParam;
import com.example.nft.entity.Consumer;
import com.example.nft.entity.OpenidPhone;
import com.example.nft.service.ConsumerService;
import com.example.nft.service.OpenidPhoneService;
import com.example.nft.service.ex.SelectException;
import com.example.nft.utils.JwtUtil;
import com.example.nft.utils.RedisUtil;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/consumer")
@Api(tags="1.用户模块")
public class ConsumerController extends BaseController {

    @Autowired
    private ConsumerService consumerService;

    @Resource
    private OpenidPhoneService openidPhoneService;


    @Resource
    private RedisUtil redisUtil;

    @PostMapping("/login")
    @ApiOperation(value="用户登录")
    public Result login(@RequestBody LoginParam loginParam){
        String result = consumerService.login(loginParam.getAccount(), loginParam.getPassword());

        return ResultGenerator.genSuccessResult(result);
    }


    @PostMapping("/add")
    @ApiOperation(value="用户注册")
    public Result add(@RequestBody RegisterParam registerParam){
        String result = consumerService.add(registerParam.getAccount(),registerParam.getPassword(),registerParam.getValid());
        if(!result.equals("success")){
            return ResultGenerator.genFailResult(500, result);
        }
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 微信通过openid 进行注册和登录，没有注册直接注册，默认密码“12345678”
     */
    @PostMapping("/wx/add")
    public synchronized Result wxAdd(@RequestBody OpenidPhone openidPhone){
        System.out.println(openidPhone);

        if(openidPhoneService.findByOpenId(openidPhone.getOpenid()) == null){
            // openid为空时，进行插入操作
            if(!openidPhoneService.add(openidPhone).equals("success")){
                return ResultGenerator.genFailResult("插入openidPhone表失败");
            }

            // 注册顾客
            String result = consumerService.add(openidPhone.getPhone(),"12345678","12345678");
            if(!result.equals("success")){
                return ResultGenerator.genFailResult(500, result);
            }
        }
        String result = openidPhoneService.findByOpenId(openidPhone.getOpenid()).getPhone();
        if(!result.equals(openidPhone.getPhone())){
            throw new SelectException("微信与手机号不匹配");
        }
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/wx/getToken/{phone}")
    public Result getToken(@PathVariable("phone") String phone){

        String token =  JwtUtil.genToken(phone);
        // 生成的token保存在redis中，key为店铺uuid拼接上用户手机号
        redisUtil.set(phone,token,60*60*24);
        return ResultGenerator.genSuccessResult(token);
    }




    @PostMapping("/status/change")
    @ApiOperation(value = "用户状态改变（状态，手机号）")
    public Result statusChange(@RequestBody ConsumerParam consumerParam){
        String result = consumerService.statusChange(consumerParam.getConsumerStatus() ,consumerParam.getConsumerPhone());
        if(!result.equals("success")){
            return ResultGenerator.genFailResult(500, result);
        }
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/change")
    @ApiOperation(value = "用户信息改变")
    public  Result change(@RequestBody Consumer consumer){

        String result = consumerService.change(consumer);
        if(!result.equals("success")){
            return ResultGenerator.genFailResult(500, result);
        }
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/password/change")
    @ApiOperation(value = "用户密码改变")
    public Result passwordChange(@RequestBody PasswordParam passwordParam){
        System.out.println(passwordParam);
        String result = consumerService.passwordChange(passwordParam.getConsumerPhone(), passwordParam.getOldPassword(),
                passwordParam.getNewPassword1(),passwordParam.getNewPassword2());

        return ResultGenerator.genSuccessResult(result);

    }

    @PostMapping("/findMany")
    @ApiOperation(value = "分页查询用户")
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
    @ApiOperation(value = "分页查询禁用用户")
    public Result findBanMany(@RequestBody PageParam param){
        List<Consumer> result = consumerService.findBanMany(param.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/count")
    @ApiOperation(value = "查询用户总数")
    public Result count(){
        int result = consumerService.count();
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/findByPhone")
    @ApiOperation(value = "查询用户通过手机号")
    public Result findByPhone(@RequestBody ConsumerParam consumerParam){
        Consumer result = consumerService.findByPhone(consumerParam.getConsumerPhone());
        if(result == null){
            return ResultGenerator.genSuccessResult(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/findByNumber")
    @ApiOperation(value = "根据车牌号查询用户")
    public Result findByNumber(@RequestBody ConsumerParam consumerParam){
        Consumer result = consumerService.findByNumber(consumerParam.getCarNumber());
        if(result == null){
            return ResultGenerator.genSuccessResult(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/findCarAndStore")
    @ApiOperation(value = "查询用户拥有的车辆和加入的店铺")
    public Result findCarAndStore(@RequestBody ConsumerParam consumerParam){
        HashMap<String, Object> result = consumerService.findCarAndStore(consumerParam.getConsumerPhone());

        return ResultGenerator.genSuccessResult(result);
    }
}















