package com.example.nft.service;

import com.example.nft.entity.ConsumeLog;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2023/2/21 下午1:33
 * @PackageName:com.example.nft.service
 * @ClassName: ConsumeLogService
 * @Description: TODO
 * @Version 1.0
 */
@Service
public interface ConsumeLogService {

    /**
     * 插入消费记录
     * @param consumeLog
     * @return
     */
    String add(ConsumeLog consumeLog);

    /**
     * 查找消费记录
     * @param storeUuid
     * @param consumerUuid
     * @param currentPage
     * @return
     */
    HashMap<String, Object> findMany(String storeUuid, String consumerUuid, Integer currentPage);
}
