package com.example.nft.controller;

import com.example.nft.controller.param.BalanceParam;
import com.example.nft.controller.param.PageParam;
import com.example.nft.dao.ConsumerMapper;
import com.example.nft.entity.ConsumerBalance;
import com.example.nft.entity.Notice;
import com.example.nft.service.ConsumerBalanceService;
import com.example.nft.service.NoticeService;
import com.example.nft.service.SendSms;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/balance")
public class ConsumerBalanceController extends BaseController{

    @Resource
    private ConsumerBalanceService consumerBalanceService;

    @Resource
    private ConsumerMapper consumerMapper;

    @Resource
    private NoticeService noticeService;

    @Resource
    private SendSms sendSms;

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
        ArrayList<ConsumerBalance> result = consumerBalanceService.findOne(balanceParam.getBalanceOwnerPhone(), balanceParam.getStoreUuid());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/findMany")
    public Result findMany(@RequestBody PageParam pageParam){
        HashMap<Object, Object> result = consumerBalanceService.findMany(pageParam.getStoreUuid(), pageParam.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }


    @PostMapping("/change")
    public Result change(@RequestBody BalanceParam balanceParam){
        String result="";
        String phone = "";
        if(balanceParam.getConsumerUuid() != null){
            // uuid转为手机号
            phone = consumerMapper.selectByUuid(balanceParam.getConsumerUuid()).getConsumerPhone();
            result = consumerBalanceService.change(phone, balanceParam.getStoreUuid() ,balanceParam.getCostMoney(), balanceParam.getCostService1(), balanceParam.getCostService2(),
                    balanceParam.getCostService3(),balanceParam.getCostService4(),balanceParam.getCostService5());
            return ResultGenerator.genSuccessResult(result);
        }
        phone = balanceParam.getBalanceOwnerPhone();
        result = consumerBalanceService.change(phone, balanceParam.getStoreUuid() ,balanceParam.getCostMoney(), balanceParam.getCostService1(), balanceParam.getCostService2(),
                balanceParam.getCostService3(),balanceParam.getCostService4(),balanceParam.getCostService5());

        if(result.equals("success")){
            HashMap<String, Object> map = new HashMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            String message = "详情：【余额变动】：" + balanceParam.getCostMoney() + "元，"+"【洗车次数】：" + balanceParam.getCostService1() +"次，"
                    + "【其他】：" + balanceParam.getCostService2() +"次";

            map.put("date", formattedDateTime);
            map.put("store", "宜城市牧车人汽车服务中心");
            map.put("service", message);
            //调用方法发送信息 传入电话，模板，验证码
            boolean send = sendSms.addSendSms(phone,  map);
        }
        return ResultGenerator.genSuccessResult(result);
    }

}
