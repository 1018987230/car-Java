package com.example.nft.service;

import com.example.nft.controller.param.ChargeOrderParam;
import com.example.nft.entity.ChargeOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxx
 * @since 2024-03-20
 */
public interface ChargeOrderService extends IService<ChargeOrder> {

    ChargeOrder createOrder(ChargeOrderParam chargeOrderParam);
}
