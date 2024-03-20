package com.example.nft.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.nft.entity.ChargeOrder;
import com.example.nft.entity.Consumer;
import com.example.nft.entity.OpenidPhone;
import com.example.nft.service.ChargeOrderService;
import com.example.nft.service.ConsumerService;
import com.example.nft.service.OpenidPhoneService;
import com.example.nft.utils.RandomUtil;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.bean.transfer.TransferBatchesRequest;
import com.github.binarywang.wxpay.bean.transfer.TransferBatchesResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.wechat.pay.java.core.http.*;
import com.wechat.pay.java.service.transferbatch.model.InitiateBatchTransferRequest;
import com.wechat.pay.java.service.transferbatch.model.InitiateBatchTransferResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.cipher.PrivacyDecryptor;
import com.wechat.pay.java.core.cipher.PrivacyEncryptor;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.http.Constant;
import com.wechat.pay.java.core.http.DefaultHttpClientBuilder;
import com.wechat.pay.java.core.http.HostName;
import com.wechat.pay.java.core.http.HttpClient;
import com.wechat.pay.java.core.http.HttpHeaders;
import com.wechat.pay.java.core.http.HttpMethod;
import com.wechat.pay.java.core.http.HttpRequest;
import com.wechat.pay.java.core.http.HttpResponse;
import com.wechat.pay.java.core.http.JsonRequestBody;
import com.wechat.pay.java.core.http.MediaType;
import com.wechat.pay.java.core.http.QueryParameter;
import com.wechat.pay.java.core.http.RequestBody;
import com.wechat.pay.java.service.transferbatch.model.GetTransferBatchByNoRequest;
import com.wechat.pay.java.service.transferbatch.model.GetTransferBatchByOutNoRequest;
import com.wechat.pay.java.service.transferbatch.model.GetTransferDetailByNoRequest;
import com.wechat.pay.java.service.transferbatch.model.GetTransferDetailByOutNoRequest;
import com.wechat.pay.java.service.transferbatch.model.InitiateBatchTransferRequest;
import com.wechat.pay.java.service.transferbatch.model.InitiateBatchTransferResponse;
import com.wechat.pay.java.service.transferbatch.model.TransferBatchEntity;
import com.wechat.pay.java.service.transferbatch.model.TransferDetailEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static com.wechat.pay.java.core.util.GsonUtil.toJson;

/**
 * @Author wangyixiong
 * @Date 2024/3/1 下午8:00
 * @PackageName:com.example.nft.controller
 * @ClassName: WxPayController
 * @Description: TODO
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/wxPay")
public class WxPayController {

    private static final String NOTIFY_URL = "https://www.bbdxgg.top/api/payNotify";

    private static final String Wechatpay_Serial = "";

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private ChargeOrderService chargeOrderService;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private OpenidPhoneService openidPhoneService;

    /**
     * 微信支付
     * @param orderNo
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/wxpay")
    public Result pay(@RequestParam("orderNo") String orderNo) {

        try {
            ChargeOrder chargeOrder = chargeOrderService.getById(orderNo);
            String id = chargeOrder.getConsumerId();
            Consumer consumer = consumerService.findByUuid(id);
            OpenidPhone openidPhone = openidPhoneService.findByPhone(consumer.getConsumerPhone());
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setBody(chargeOrder.getNote());
            orderRequest.setOutTradeNo(orderNo);
            orderRequest.setTotalFee(chargeOrder.getChargeMoneyAmount());//元转成分
            orderRequest.setOpenid(openidPhone.getOpenid());
            orderRequest.setSpbillCreateIp("120.25.125.1");
            orderRequest.setTimeStart("202312121212");
            orderRequest.setTotalFee(chargeOrder.getChargeMoneyAmount());
            orderRequest.setNotifyUrl(NOTIFY_URL + "/wx/pay");
            orderRequest.setTradeType("JSAPI");
            Object order = wxPayService.createOrder(orderRequest);
            System.out.println(order.toString());
            return ResultGenerator.genSuccessResult(order);
        } catch (Exception e) {
            log.error("微信支付失败！订单号：{},原因:{}", orderNo, e.getMessage());
            e.printStackTrace();
            return ResultGenerator.genFailResult("支付失败，请稍后重试！");
        }
    }

    /**
     * 用户提现
     * @return
     */
    @PostMapping("/withdraw")
    public Result Withdraw(){
        try {
            TransferBatchesRequest transferBatchesRequest = new TransferBatchesRequest();
            transferBatchesRequest.setAppid("wx30daae2411af79a8");
            transferBatchesRequest.setBatchName("转账");
            transferBatchesRequest.setTotalAmount(3);
            transferBatchesRequest.setBatchRemark("备注");
            transferBatchesRequest.setOutBatchNo(RandomUtil.generateRandom(6));
            transferBatchesRequest.setTotalNum(1);
            List<TransferBatchesRequest.TransferDetail> list = new ArrayList<>();
            TransferBatchesRequest.TransferDetail transferDetail = new TransferBatchesRequest.TransferDetail();
            transferDetail.setOpenid("o3ZmQ5CvE5yCamXbfzWFaGin_oUk");
            transferDetail.setOutDetailNo(RandomUtil.generateRandom(5));
            transferDetail.setTransferAmount(3);
            transferDetail.setTransferRemark("收钱");
            list.add(transferDetail);
            transferBatchesRequest.setTransferDetailList(list);
            // 调用 initiateBatchTransfer 方法
            TransferBatchesResult result = wxPayService.getTransferService().transferBatches(transferBatchesRequest);
            System.out.println(result);
            // 处理响应
        } catch (Exception e) {
            // 处理异常
            System.out.println(e);
        }
        return ResultGenerator.genSuccessResult();
    }
    private RequestBody createRequestBody(Object request) {
        return new JsonRequestBody.Builder().body(toJson(request)).build();
    }

}
