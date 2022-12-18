package com.example.nft.controller.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author wangyixiong
 * @Date 2022/10/23 下午11:40
 * @PackageName:com.example.nft.controller.param
 * @ClassName: NoticeParam
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class NoticeParam implements Serializable {

    private String noticeUuid;

    private Integer noticeStatus;

    private Integer consumerId;

    private Integer storeId;


}
