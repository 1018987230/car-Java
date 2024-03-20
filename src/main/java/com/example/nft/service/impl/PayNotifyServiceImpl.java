package com.example.nft.service.impl;

import com.example.nft.dao.ChargeOrderMapper;
import com.example.nft.dao.ConsumerMapper;
import com.example.nft.entity.ChargeOrder;
import com.example.nft.entity.Consumer;
import com.example.nft.service.ConsumerBalanceService;
import com.example.nft.service.PayNotifyService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wangyixiong
 * @Date 2024/3/20 下午9:24
 * @PackageName:com.example.nft.service.impl
 * @ClassName: PayServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class PayNotifyServiceImpl implements PayNotifyService {

    @Autowired
    private ChargeOrderMapper chargeOrderMapper;

    @Autowired
    private ConsumerBalanceService consumerBalanceService;

    @Autowired
    private ConsumerMapper consumerMapper;

    @Override
    public String chargeNotify(WxPayOrderNotifyResult wxPayOrderNotifyResult) {

        ChargeOrder chargeOrder = chargeOrderMapper.selectById(wxPayOrderNotifyResult.getOutTradeNo());
        chargeOrder.setStatus(2);
        chargeOrder.setTransactionId(wxPayOrderNotifyResult.getTransactionId());
        chargeOrderMapper.updateById(chargeOrder);

        Consumer consumer = consumerMapper.selectByUuid(chargeOrder.getConsumerId());

        consumerBalanceService.change(consumer.getConsumerPhone(), chargeOrder.getStoreId(),chargeOrder.getChargeMoneyAmount() ,0,0,0,0,0);
        return "SUCCESS";
    }
}
