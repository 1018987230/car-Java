package com.example.nft.dao;


import com.example.nft.entity.Consumer;
import com.example.nft.entity.ConsumerStore;
import com.example.nft.entity.Store;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConsumerStoreMapper {

    // 查询；联合主键是否重复
    ConsumerStore selectConsumerStore(String consumerUuid, String storeUuid);

    // 顾客和店铺建立关系
    Integer consumerAndStore(String consumerUuid, String storeUuid);

    // 删除顾客和店铺的关联
    Integer deleteConsumerStore(String consumerUuid, String storeUuid);

    // 通过顾客id获取加入的店铺
    List<Store> selectStoreByConsumerUuid(String consumerUuid);
    Integer selectStoreCountByConsumerUuid(String consumerUuid);

    // 通过店铺id获取所有顾客id
    List<Consumer> selectConsumerByStoreUuid(String storeUuid, Integer currentPage);
    Integer selectConsumerCountByStoreUuid(String storeUuid);


}
