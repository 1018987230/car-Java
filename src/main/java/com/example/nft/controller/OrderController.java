package com.example.nft.controller;

import com.example.nft.controller.param.ConsumerStoreParam;
import com.example.nft.controller.param.OrderIdParam;
import com.example.nft.controller.param.OrderParam;
import com.example.nft.controller.param.PageParam;
import com.example.nft.entity.Order;
import com.example.nft.service.ConsumerStoreService;
import com.example.nft.service.OrderService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2022/10/23 上午2:11
 * @PackageName:com.example.nft.controller
 * @ClassName: OrderController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/order")
public class OrderController  extends BaseController{


    @Resource
    private OrderService orderService;



    @PostMapping("/add")
    public Result add(@RequestBody OrderParam orderParam){
        System.out.println(orderParam);
        String result = orderService.addOrder(orderParam.getOrderConsumerUuid(),orderParam.getOrderStoreUuid(),orderParam.getOrderCarNumber(),orderParam.getOrderService(),orderParam.getOrderNote(),orderParam.getOrderPreordainTime());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/change")
    public Result change(@RequestBody OrderIdParam orderIdParam){
        String result = orderService.changeStatus(orderIdParam.getOrderUuid(), orderIdParam.getOrderStatus());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/findByUuid")
    public Result findByUuid(@RequestBody OrderIdParam orderIdParam){
        List<Order> result = orderService.findByUuid(orderIdParam.getOrderUuid());
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/findByConsumerId")
    public Result findByConsumerId(@RequestBody PageParam pageParam){
        List<Order> result = orderService.findByConsumerUuid(pageParam.getConsumerUuid(), pageParam.getStoreUuid(),pageParam.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }



    @PostMapping("/findByConsumerPhone")
    public Result findByConsumerPhone(@RequestBody PageParam pageParam){

        List<Order> result = orderService.findByConsumerPhone(pageParam.getConsumerPhone(), pageParam.getStoreUuid(), pageParam.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }



    @PostMapping("/findByStoreId")
    public Result findByStoreId(@RequestBody ConsumerStoreParam consumerStoreParam){
        HashMap<String, Object> result = orderService.findByStoreUuid(consumerStoreParam.getStoreUuid(), consumerStoreParam.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }


    @PostMapping("/findByCarNumber")
    public Result findByCarNumber(@RequestBody PageParam pageParam){
        List<Order> result = orderService.findByCarNumber(pageParam.getCarNumber(), pageParam.getStoreUuid(),pageParam.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }


}
