package com.example.nft.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author ：bbdxgg
 * @date ：Created By 2023/1/5 上午10:22
 * @description：
 * @modified By：
 * @version: $
 */
@Service
public interface ProductLogService {

    /**
     * 分页查询+条件查询
     * @param storeUuid
     * @param productUuid
     * @param currentPage
     * @return
     */
    HashMap<String, Object> findMany(String storeUuid, String productUuid, String productName,Integer currentPage);


}
