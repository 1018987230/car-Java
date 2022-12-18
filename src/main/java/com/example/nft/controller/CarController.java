package com.example.nft.controller;

import com.example.nft.controller.param.CarParam;
import com.example.nft.controller.param.PageParam;
import com.example.nft.entity.Car;
import com.example.nft.service.CarService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController extends BaseController{


    @Resource
    private CarService carService;


    @PostMapping("/add")
    public Result add(@RequestBody Car car){
        System.out.println(car);
        String result = carService.add(car);
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/remove")
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

}
