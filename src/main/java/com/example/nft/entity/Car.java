package com.example.nft.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_car")
public class Car {

    @TableField("car_id")
    private Integer carId;

    @TableField("car_owner_phone")
    private String carOwnerPhone;

    @TableField("car_type")
    private String carType;

    @TableField("car_number")
    private String carNumber;

    @TableField("car_config_1")
    private String carConfig1;

    @TableField("car_config_2")
    private String carConfig2;

    @TableField("car_config_3")
    private String carConfig3;

    @TableField("car_config_4")
    private String carConfig4;

    @TableField("car_config_5")
    private String carConfig5;

    @TableField("create_time")
    private String createTime;
}
