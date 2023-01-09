package com.example.nft.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="consumer", description="用户属性")
public class Consumer {

    @ApiModelProperty(value="主键")
    private Integer consumerId;

    private String consumerUuid;
    private String consumerSalt;
    private String consumerName;
    private String consumerSex;
    private String consumerBirthday;
    private String consumerLevel;
    private String consumerPhone;
    private String consumerPassword;
    private Integer consumerStatus;
    private String createTime;

    private List<Store> stores;
}
