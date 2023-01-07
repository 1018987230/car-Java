package com.example.nft.service.impl;

import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.dao.ProductLogMapper;
import com.example.nft.dao.ProductMapper;
import com.example.nft.entity.Product;
import com.example.nft.entity.ProductLog;
import com.example.nft.service.ProductService;
import com.example.nft.service.ex.InsertException;
import com.example.nft.service.ex.ServiceException;
import com.example.nft.service.ex.UpdateException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：bbdxgg
 * @date ：Created By 2023/1/5 上午10:23
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductLogMapper productLogMapper;

    @Override
    public String add(Product product) {

        // 生成uuid
        String productUuid ="P" + (new Date().getTime() + (int)((Math.random()*9+1)*100000) + "").substring(4);
        product.setProductUuid(productUuid);

        Boolean dbRes = productMapper.insert(product);
        if (!dbRes) {
            throw new InsertException(ServiceResultEnum.DB_INSERT_ERROR.getResult());
        }

        // insert product log
        ProductLog productLog = new ProductLog();

        productLog.setStoreUuid(product.getStoreUuid());
        productLog.setProductUuid(productUuid);
        productLog.setProductName(product.getProductName());
        productLog.setProductLogQuantity(product.getProductQuantity());
        productLog.setProductUnit(product.getProductUnit());
        productLog.setProductLogPrice(product.getProductPrice());
        productLog.setProductLogType(0);
        System.out.println(productLog);
        if(productLogMapper.insert(productLog) == 0){
            throw new InsertException(ServiceResultEnum.DB_INSERT_ERROR.getResult());
        }

        return ServiceResultEnum.SUCCESS.getResult();
    }


    @Override
    public String changeInfo(Product product) {

        // does productUuid existed
        if(productMapper.selectByUuid(product.getProductUuid()) == null){
            throw new ServiceException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }

        Integer dbRes = productMapper.updateInfoByUuid(product.getProductUuid(), product.getProductName(),
                product.getProductPrice(), product.getProductUnit(), product.getProductIn());
        if(dbRes == 0){
            throw new UpdateException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }
        return ServiceResultEnum.SUCCESS.getResult();
    }


    @Override
    public String changeQuantity(String productUuid, Integer productQuantity) {

        // does productUuid existed
        Product product = productMapper.selectByUuid(productUuid);
        if(product == null){
            throw new ServiceException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }

        int productType;
        if(productQuantity < 0){
            // 数量发生改变需要记录到productLog
            productType = 1;
        }else {
            productType = 2;
        }

        productQuantity = product.getProductQuantity() + productQuantity;
        // update product quantity
        if(productMapper.updateQuantityByUuid(productUuid, productQuantity) == 0){
            throw new ServiceException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }


        // insert product log
        ProductLog productLog = new ProductLog();

        productLog.setStoreUuid(product.getStoreUuid());
        productLog.setProductUuid(productUuid);
        productLog.setProductName(product.getProductName());
        productLog.setProductLogQuantity(product.getProductQuantity());
        productLog.setProductUnit(product.getProductUnit());
        productLog.setProductLogPrice(product.getProductPrice());
        productLog.setProductLogType(productType);
        System.out.println(productLog);
        if(productLogMapper.insert(productLog) == 0){
            throw new InsertException(ServiceResultEnum.DB_INSERT_ERROR.getResult());
        }

        return ServiceResultEnum.SUCCESS.getResult();
    }

    @Override
    public String changeStatus(String productUuid, Integer productStatus) {

        Product dbRes = productMapper.selectByUuid(productUuid);
        // does productUuid existed
        if(dbRes == null){
            throw new ServiceException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        // update status
        if(productMapper.updateStatusByUuid(productUuid, productStatus) == 0){
            throw new ServiceException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }

        // insert product log
        ProductLog productLog = new ProductLog();

        productLog.setStoreUuid(dbRes.getStoreUuid());
        productLog.setProductUuid(productUuid);
        productLog.setProductName(dbRes.getProductName());
        productLog.setProductLogQuantity(dbRes.getProductQuantity());
        productLog.setProductUnit(dbRes.getProductUnit());
        productLog.setProductLogPrice(dbRes.getProductPrice());
        productLog.setProductLogType(3); //删除
        System.out.println(productLog);
        if(productLogMapper.insert(productLog) == 0){
            throw new InsertException(ServiceResultEnum.DB_INSERT_ERROR.getResult());
        }

        return ServiceResultEnum.SUCCESS.getResult();
    }

    @Override
    public HashMap<String, Object> findMany(String storeUuid, String productName, Integer currentPage) {
        HashMap<String, Object> map = new HashMap<>();

        List<Product> info = productMapper.selectMany(storeUuid, productName, (currentPage-1)*20);
        Integer sum = productMapper.selectCount(storeUuid, productName, currentPage);
        map.put("info", info);
        map.put("sum", sum);

        return map;
    }

    @Override
    public Product findByUuid(String productUuid) {
        return productMapper.selectByUuid(productUuid);
    }
}
