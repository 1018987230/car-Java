package com.example.nft.dao;

import com.example.nft.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2022/10/23 下午9:28
 * @PackageName:com.example.nft.dao
 * @ClassName: NoticeMapper
 * @Description: TODO
 * @Version 1.0
 */
@Mapper
public interface NoticeMapper {

    Integer insertNotice(Notice notice);

    Integer updateStatus(String noticeUuid, Integer noticeStatus);

    Notice selectByUuid(String noticeUuid);

    List<Notice> selectByConsumerUuid(String consumerUuid, Integer currentPage);

    List<Notice> selectByStoreUuid(String storeUuid, Integer currentPage);


    /**
     * @Description 根据店铺uuid查询通知消息
     */
    List<Notice> selectConsumerChangeByStore(String storeUuid, String noticeTitle, Integer currentPage);

    Integer selectCount();
}
