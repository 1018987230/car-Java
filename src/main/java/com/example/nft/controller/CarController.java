package com.example.nft.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.ocr_api20210707.models.RecognizeCarNumberResponse;
import com.example.nft.controller.param.CarParam;
import com.example.nft.controller.param.PageParam;
import com.example.nft.controller.param.RecognizeParam;
import com.example.nft.controller.vo.RecognizeVo;
import com.example.nft.entity.Car;
import com.example.nft.entity.Consumer;
import com.example.nft.entity.ConsumerBalance;
import com.example.nft.service.CarService;
import com.example.nft.service.ConsumerBalanceService;
import com.example.nft.service.ConsumerService;
import com.example.nft.service.SendSms;
import com.example.nft.service.ex.SelectException;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/car")
@Api(tags = "4.车辆模块")
public class CarController extends BaseController{


    @Resource
    private CarService carService;

    @Resource
    private ConsumerService consumerService;

    @Resource
    private ConsumerBalanceService consumerBalanceService;





    @PostMapping("/add")
    @ApiOperation(value = "用户添加车辆")
    public Result add(@RequestBody Car car){
        String result = carService.add(car);

        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/remove")
    @ApiOperation(value = "用户删除车辆")
    public Result remove(@RequestBody CarParam carParam){
        String result = carService.removeByNumber(carParam.getCarNumber());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/change")
    public Result change(@RequestBody Car car){
        String result = carService.changeByNumber(car);
        return ResultGenerator.genSuccessResult(result);
    }


    @PostMapping("/findByNumber")
    public Result findByNumber(@RequestBody PageParam pageParam){
        Car result = carService.findByNumber(pageParam.getStoreUuid(), pageParam.getCarNumber());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/findByPhone")
    public Result findByPhone(@RequestBody PageParam pageParam){
        List<Car> result = carService.findByPhone(pageParam.getStoreUuid(), pageParam.getConsumerPhone());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/findByStore")
    public Result findByStore(@RequestBody PageParam pageParam){
        HashMap<String, Object> result = carService.findByStore(pageParam.getStoreUuid(), pageParam.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/recognize")
    public Result recognize(@RequestParam("file") MultipartFile file, @RequestParam("storeUuid") String storeUuid) throws Exception {
        String licensePlate;
        // 识别车牌
        try {
            RecognizeCarNumberResponse result = carService.recognize(file);
            String jsonString = JSON.parseObject(result.body.data).get("data").toString();
            JSONObject outerJsonObject = JSON.parseObject(jsonString);
            JSONObject dataJsonObject = outerJsonObject.getJSONObject("data");
            licensePlate = dataJsonObject.getString("车牌");
        }catch (Exception e){
            return ResultGenerator.genFailResult("车牌识别失败！请重试！");
        }
        // 查询信息
        if(licensePlate == null){
            return ResultGenerator.genFailResult("车牌识别结果为空！请重试！");
        }
        Car car = carService.findByNumber(storeUuid, licensePlate);
        if(car == null){
            throw new SelectException("车牌号不存在！");
        }
        Consumer consumer = consumerService.findByNumber(car.getCarNumber());
        List<ConsumerBalance> balanceServiceOne = consumerBalanceService.findOne(consumer.getConsumerPhone(), storeUuid);

        // 拼接信息
        RecognizeVo vo = new RecognizeVo();
        vo.setCarNumber(licensePlate);
        vo.setConsumerUuid(consumer.getConsumerUuid());
        vo.setConsumerName(consumer.getConsumerName());
        vo.setConsumerPhone(consumer.getConsumerPhone());
        vo.setBalanceMoney(balanceServiceOne.get(0).getBalanceMoney());
        vo.setBalanceService1(balanceServiceOne.get(0).getBalanceService1());
        vo.setBalanceService2(balanceServiceOne.get(0).getBalanceService2());
        vo.setBalanceService3(balanceServiceOne.get(0).getBalanceService3());
        vo.setBalanceService4(balanceServiceOne.get(0).getBalanceService4());
        vo.setBalanceService5(balanceServiceOne.get(0).getBalanceService5());

        return ResultGenerator.genSuccessResult(vo);
    }

}
