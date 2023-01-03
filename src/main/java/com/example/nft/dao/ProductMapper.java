package com.example.nft.dao;

import com.example.nft.entity.Product;

import java.util.List;

/**
 * @author ：bbdxgg
 * @date ：Created By 2023/1/3 上午10:18
 * @description：
 * @modified By：
 * @version: $
 */
public interface ProductMapper {

    /**
     * 新增产品
     * @param product
     * @return
     */
    Integer insert( Product product);

    /**
     * 更改产品非重要信息，(不更改数量)
     * @param productUuid
     * @param
     * @return
     */
    Integer updateInfoByUuid(String productUuid, String productName, Float productPrice,String productUnit, String productIn);


    /**
     * 更新产品数量
     * @param productUuid
     * @param productQuantity
     * @return
     */
    Integer updateByQuantity(String productUuid, Integer productQuantity);

    /**
     *  分页查询店铺下的原材料列表
     * @param storeUuid
     * @param currentPage
     * @return
     */
    List<Product> selectByStore(String storeUuid, Integer currentPage);

    /**
     * 根据产品名模糊查询
     * @param storeUuid
     * @param productName
     * @return
     */
    List<Product> selectByName(String storeUuid, String productName);


    /**
     * 根据产品uuid精确查找
     * @param storeUuid
     * @param productUuid
     * @return
     */
    Product selectByUuid(String storeUuid, String productUuid);




}
