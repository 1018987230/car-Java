package com.example.nft.entity;

import lombok.Data;

/**
 * @Author wangyixiong
 * @Date 2023/2/21 下午1:31
 * @PackageName:com.example.nft.entity
 * @ClassName: ConsumeLog
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class ConsumeLog {

    private String consumeLogId;
    private String consumeLogConsumer;
    private String consumeLogStore;
    private String consumeLogMoney;
    private String consumeLogService;
    private String consumeLogServiceNum;
    private String consumeLogSource;
    private Integer consumeLogStatus;
    private String createTime;
}
