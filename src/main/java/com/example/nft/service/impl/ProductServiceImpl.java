package com.example.nft.service.impl;

import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.dao.ProductMapper;
import com.example.nft.entity.Product;
import com.example.nft.service.ProductService;
import com.example.nft.service.ex.InsertException;
import com.example.nft.service.ex.ServiceException;
import com.example.nft.service.ex.UpdateException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public String add(Product product) {
        Integer dbRes = productMapper.insert(product);
        if (dbRes == 0) {
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
        if(productMapper.selectByUuid(productUuid) == null){
            throw new ServiceException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }

        // update product quantity
        if(productMapper.updateQuantityByUuid(productUuid, productQuantity) == 0){
            throw new ServiceException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }

        return ServiceResultEnum.SUCCESS.getResult();
    }

    @Override
    public String changeStatus(String productUuid, Integer productStatus) {

        // does productUuid existed
        if(productMapper.selectByUuid(productUuid) == null){
            throw new ServiceException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        // update status
        if(productMapper.updateStatusByUuid(productUuid, productStatus) == 0){
            throw new ServiceException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }
        return ServiceResultEnum.SUCCESS.getResult();
    }

    @Override
    public List<Product> findMany(String storeUuid, String productName, Integer currentPage) {
        return productMapper.selectMany(storeUuid, productName, currentPage);
    }

    @Override
    public Product findByUuid(String productUuid) {
        return productMapper.selectByUuid(productUuid);
    }
}
