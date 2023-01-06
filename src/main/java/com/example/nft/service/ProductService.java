package com.example.nft.service;

import com.example.nft.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：bbdxgg
 * @date ：Created By 2023/1/5 上午10:22
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public interface ProductService {

    /**
     * 新增产品
     * @param product
     * @return
     */
    String add(Product product);


    /**
     * 修改非重要信息
     * @param product
     * @return
     */
    String changeInfo(Product product);


    /**
     * 修改产品数量
     * @param productUuid
     * @param productQuantity
     * @return
     */
    String changeQuantity(String productUuid, Integer productQuantity);


    /**
     * 修改产品的状态
     * @param productUuid
     * @param productStatus
     * @return
     */
    String changeStatus(String productUuid, Integer productStatus);


    /**
     * 分页查询（首次尝试分页查询＋条件查询）
     * @param storeUuid
     * @param currentPage
     * @return
     */
    List<Product> findMany(String storeUuid, String productName,Integer currentPage);

    /**
     * 根据产品uuid查询
     * @param productUuid
     * @return
     */
    Product findByUuid(String productUuid);



}
