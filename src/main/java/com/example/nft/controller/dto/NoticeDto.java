package com.example.nft.controller.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author wangyixiong
 * @Date 2023/9/2 下午11:02
 * @PackageName:com.example.nft.controller.dto
 * @ClassName: NoticeDto
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class NoticeDto implements Serializable {

    private Integer noticeId;
    private String noticeUuid;
    private String noticeFrom;
    private String noticeTo;
    private String noticeTitle;
    private String noticeContent;
    private Integer noticeStatus;
    private String createTime;

}
