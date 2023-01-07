package com.example.nft.dao;

import com.example.nft.entity.ProductLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ：bbdxgg
 * @date ：Created By 2023/1/3 上午10:18
 * @description：
 * @modified By：
 * @version: $
 */
@Mapper
public interface ProductLogMapper {

    /**
     * 插入产品增删记录
     * @param productLog
     * @return
     */
    Integer insert(ProductLog productLog);



    /**
     * 根据产品的uuid查询记录
     * @param storeUuid
     * @param
     * @return
     */
    List<ProductLog> selectMany(String storeUuid, String productUuid, String productName, Integer currentPage);
    Integer selectCount(String storeUuid, String productUuid, String productName);



}
