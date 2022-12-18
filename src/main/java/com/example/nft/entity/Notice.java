package com.example.nft.entity;

import lombok.Data;

/**
 * @Author wangyixiong
 * @Date 2022/10/23 下午9:25
 * @PackageName:com.example.nft.entity
 * @ClassName: Notice
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class Notice {
    private Integer noticeId;
    private String noticeUuid;
    private String noticeFrom;
    private String noticeTo;
    private String noticeTitle;
    private String noticeContent;
    private Integer noticeStatus;
    private String createTime;
}
