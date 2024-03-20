package com.example.nft.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 充值活动表
 * </p>
 *
 * @author xxx
 * @since 2024-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_charge_activity")
public class ChargeActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    private String id;

    /**
     * 哪个店铺开启
     */
    private String storeId;

    /**
     * 充值金额限制
     */
    private Integer chargeMoneyLimit;

    /**
     * 赠送金额
     */
    private Integer giftMoney;

    /**
     * 充值服务名称
     */
    private String chargeServiceName;

    /**
     * 充值服务次数限制
     */
    private String chargeServiceLimit;

    /**
     * 充值服务次数限制
     */
    private String giftService;

    /**
     * eg,充值500赠送300，充值金额为800
     */
    private String note;

    /**
     * 状态 0-正常 1-停止 2-取消 
     */
    private Integer status;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建时间
     */
    private Date createTime;


}
