package com.example.nft.service.impl;

import com.example.nft.dao.ProductLogMapper;
import com.example.nft.entity.ProductLog;
import com.example.nft.service.ProductLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class ProductLogServiceImpl implements ProductLogService {

    @Resource
    private ProductLogMapper productLogMapper;

    @Override
    public HashMap<String, Object> findMany(String storeUuid, String productUuid, String productName, Integer currentPage) {
        HashMap<String, Object> map = new HashMap<>();

        List<ProductLog> info = productLogMapper.selectMany(storeUuid, productUuid, productName, (currentPage-1)*20);
        Integer sum = productLogMapper.selectCount(storeUuid, productUuid, productName);
        map.put("info", info);
        map.put("sum", sum);

        return map;
    }
}
