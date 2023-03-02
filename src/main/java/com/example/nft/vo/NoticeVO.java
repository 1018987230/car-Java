package com.example.nft.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author wangyixiong
 * @Date 2023/2/19 下午10:11
 * @PackageName:com.example.nft.vo
 * @ClassName: NoticeVO
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class NoticeVO implements Serializable {
    private String noticeUuid;
    private String noticeFrom;
    private String noticeTo;
    private String noticeStoreName; // 店铺名字
    private String noticeTitle;
    private String noticeContent;
    private Integer noticeStatus;
    private String createTime;
}
