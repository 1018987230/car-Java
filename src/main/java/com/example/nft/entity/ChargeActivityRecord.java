package com.example.nft.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 充值活动参与表
 * </p>
 *
 * @author xxx
 * @since 2024-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_charge_activity_record")
public class ChargeActivityRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动参与记录id
     */
    private String id;

    /**
     * 参与用户
     */
    private String consumerId;

    /**
     * 参与活动
     */
    private String activityId;

    /**
     * 创建时间
     */
    private Date createTime;


}
