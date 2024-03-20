package com.example.nft.service;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import org.springframework.stereotype.Service;

/**
 * @Author wangyixiong
 * @Date 2024/3/20 下午9:24
 * @PackageName:com.example.nft.service
 * @ClassName: PayService
 * @Description: TODO
 * @Version 1.0
 */
@Service
public interface PayNotifyService {

    /**
     * 充值支付回调
     * @return
     */
    String chargeNotify(WxPayOrderNotifyResult wxPayOrderNotifyResult);

}
