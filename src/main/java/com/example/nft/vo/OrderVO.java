package com.example.nft.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author wangyixiong
 * @Date 2023/2/19 下午10:11
 * @PackageName:com.example.nft.vo
 * @ClassName: OrderVO
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class OrderVO implements Serializable {
    private String orderUuid;
    private String orderConsumerUuid;
    private String orderStoreUuid;
    private String orderStoreName; // 店铺名字
    private String orderCarNumber;
    private String orderService;
    private String orderStatus;
    private String orderNote;
    private String orderPreordainTime;
    private String updateTime;
    private String createTime;
}
