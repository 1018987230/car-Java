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
     * 分页查询店铺的产品记录
     * @param storeUuid
     * @param currentPage
     * @return
     */
    List<ProductLog> selectByStore(String storeUuid, Integer currentPage);

    /**
     * 根据产品的uuid查询记录
     * @param storeUuid
     * @param productLogUuid
     * @return
     */
    List<ProductLog> selectByUuid(String storeUuid, String productLogUuid, Integer currentPage);


    /**
     * 根据产品的名字查询记录
     * @param storeUuid
     * @param productLogName
     * @return
     */
    List<ProductLog> selectByName(String storeUuid, String productLogName, Integer currentPage);
}
