package com.example.nft.controller;

import com.example.nft.service.PayNotifyService;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author wangyixiong
 * @Date 2024/3/20 上午8:56
 * @PackageName:com.example.nft.controller
 * @ClassName: PayNotify
 * @Description: TODO
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/payNotify")
public class PayNotify {

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private PayNotifyService payNotifyService;

    @ResponseBody
    @RequestMapping("/wx/pay")
    public String payNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(xmlResult);
            log.info(result.toString());
            payNotifyService.chargeNotify(result);
            // 加入自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
            String orderId = result.getOutTradeNo();
            String transactionId = result.getTransactionId();
            String totalFee = BaseWxPayResult.fenToYuan(result.getTotalFee());
            return WxPayNotifyResponse.success("处理成功!");
        } catch (Exception e) {
            log.error("微信回调结果异常,异常原因{}", e.getMessage());
            return WxPayNotifyResponse.fail(e.getMessage());
        }
    }

}
