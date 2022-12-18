package com.example.nft.service;

import com.example.nft.entity.Notice;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2022/10/23 下午10:52
 * @PackageName:com.example.nft.service
 * @ClassName: NoticeService
 * @Description: TODO
 * @Version 1.0
 */
@Service
public interface NoticeService {

    String add(Notice notice);

    String changeStatus(String noticeUuid, Integer noticeStatus);

    Notice findByUuid(String noticeUuid);

    List<Notice> findByConsumerUuid(String consumerId ,Integer currentPage);

    HashMap<String, Object> findByStoreUuid(String storeUuid, Integer currentPage);


    /**
     * @Description 查找店铺下的通知信息
     * @param storeUuid
     * @param noticeTitle
     * @param currentPage
     * @return
     */
    List<Notice> findConsumerChangeByStore(String storeUuid, String noticeTitle, Integer currentPage);
}
