package com.example.nft.dao;

import com.example.nft.entity.ConsumeLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2023/2/21 下午1:40
 * @PackageName:com.example.nft.dao
 * @ClassName: ConsumeLogMapper
 * @Description: TODO
 * @Version 1.0
 */
@Mapper
public interface ConsumeLogMapper {

    Integer insert(ConsumeLog consumeLog);

    /**
     *
     */
    List<ConsumeLog> selectMany(String consumerUuid, String storeUuid, Integer currentPage);
    Integer selectManyCount(String consumerUuid, String storeUuid);
}
