package com.example.nft.controller.param;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wangyixiong
 * @Date 2024/3/20 下午5:05
 * @PackageName:com.example.nft.controller.param
 * @ClassName: ChargeOrderParam
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class ChargeOrderParam implements Serializable {

    /**
     * 充值订单id
     */
    private String id;

    /**
     * 充值用户
     */
    private String consumerId;

    private String storeId;

    /**
     * 微信订单号
     */
    private String transactionId;

    /**
     * 充值金额
     */
    private Integer chargeMoneyAmount;

    /**
     * 充值服务名称
     */
    private String chargeServiceName;

    /**
     * 充值服务次数
     */
    private String chargeServiceAmount;

    /**
     * 备注
     */
    private String note;

    /**
     * 订单状态 0-待支付 1-已取消 2-已支付 3-已完成 4-退款中 5-已退款
     */
    private Integer status;

}
