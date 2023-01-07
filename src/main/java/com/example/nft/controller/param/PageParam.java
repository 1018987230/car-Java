package com.example.nft.controller.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：bbdxgg
 * @date ：Created By 2022/7/4 下午1:48
 * @description：页码参数
 * @modified By：
 * @version: $
 */
@Data
public class PageParam implements Serializable {
    private String consumerUuid;

    private String storeUuid;

    private String noticeTitle;

    private String consumerPhone;

    private String noticeUuid;

    private String productUuid;

    private String productName;

    private String carNumber;

    private Integer currentPage;
}
