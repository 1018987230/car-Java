package com.example.nft.controller;


import com.example.nft.controller.param.ChargeOrderParam;
import com.example.nft.entity.ChargeOrder;
import com.example.nft.service.ChargeOrderService;
import com.example.nft.service.ConsumerService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  用户充值
 * </p>
 *
 * @author xxx
 * @since 2024-03-20
 */
@RestController
@RequestMapping("/api/chargeOrder")
public class ChargeOrderController {

    @Autowired
    private ChargeOrderService chargeOrderService;

    @Autowired
    private ConsumerService consumerService;

    /**
     * 创建充值订单
     * @param chargeOrderParam
     * @return
     */
    @PostMapping("/createOrder")
    public Result createOrder(@RequestBody ChargeOrderParam chargeOrderParam){

        ChargeOrder result = chargeOrderService.createOrder(chargeOrderParam);
        if(result == null){
            return ResultGenerator.genFailResult("订单创建失败！");
        }

        return ResultGenerator.genSuccessResult(result);
    }
}

