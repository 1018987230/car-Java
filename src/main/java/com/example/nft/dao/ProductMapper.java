package com.example.nft.dao;

import com.example.nft.entity.Product;
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
public interface ProductMapper {

    /**
     * 新增产品
     * @param product
     * @return
     */
    Boolean insert(Product product);

    /**
     * 更改产品非重要信息，(不更改数量)
     * @param productUuid
     * @param
     * @return
     */
    Integer updateInfoByUuid(String productUuid, String productName, Integer productPrice,String productUnit, String productIn);


    /**
     * 更新产品数量
     * @param productUuid
     * @param productQuantity
     * @return
     */
    Integer updateQuantityByUuid(String productUuid, Integer productQuantity);


    /**
     * 修改产品状态
     * @param productUuid
     * @param productStatus
     * @return
     */
    Integer updateStatusByUuid(String productUuid, Integer productStatus);


    /**
     *  分页查询店铺下的原材料列表
     * @param storeUuid
     * @param currentPage
     * @return
     */
    List<Product> selectMany(String storeUuid, String productName, Integer currentPage);


    /**
     * 查询数量
     * @param storeUuid
     * @param productName
     * @return
     */
    Integer selectCount(String storeUuid, String productName, Integer currentPage);


    /**
     * 根据产品uuid精确查找
     * @param
     * @param productUuid
     * @return
     */
    Product selectByUuid(String productUuid);




}
