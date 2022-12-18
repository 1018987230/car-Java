package com.example.nft.dao;

import com.example.nft.entity.Consumer;
import com.example.nft.entity.Store;
import org.apache.catalina.session.StoreBase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoreMapper {
    Boolean insert(Store store);

    // 查询所有的店铺
    List<Store> selectAll();

    // 通过店铺名字查询店铺(以后会用上模糊查询 肯定是个列表)
    List<Store> selectStoreByName(String storeName);

    Store selectById(Integer storeId);

    /**
     * 根据uuid查询店铺
     * @param storeUuid
     * @return
     */
    Store selectByUuid(String storeUuid);

}
