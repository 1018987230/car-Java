package com.example.nft.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xxx
 * @since 2024-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_charge_order")
public class ChargeOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 充值订单id
     */
    private String id;

    /**
     * 充值用户
     */
    private String consumerId;

    /**
     * 充值店铺
     */
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
     * eg,充值500赠送300，充值金额为800
     */
    private String note;

    /**
     * 订单状态 0-待支付 1-已取消 2-已支付 3-已完成 4-退款中 5-已退款
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;


}
