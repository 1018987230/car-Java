package com.example.nft.service.impl;


import com.example.nft.dao.StoreMapper;
import com.example.nft.entity.Order;
import com.example.nft.entity.Store;
import com.example.nft.entity.StoreSetting;
import com.example.nft.service.StoreService;
import com.example.nft.service.StoreSettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Resource
    private StoreMapper storeMapper;

    @Resource
    private StoreSettingService storeSettingService;

    /**
     * 添加店铺
     * @param store
     * @return
     */
    public Boolean add(Store store){
        // 因为店铺很有可能重名，所以不进行重复判断，无影响
        // 生成伪随机六位数 + 当前时间戳
        String storeUuid ="B" + (new Date().getTime() + (int)((Math.random()*9+1)*100000) + "").substring(2);
        store.setStoreUuid(storeUuid);
        storeSettingService.add(storeUuid);
        return storeMapper.insert(store);
    }

    /**
     * 查询所有店铺
     * @param
     * @return
     */
    public List<Store> findAll(){
        return storeMapper.selectAll();
    }


    /**
     * 根据传入的店铺名字查询
     * @param storeName
     * @return
     */
    public List<Store> findStoreByName(String storeName){

        return storeMapper.selectStoreByName(storeName);
    }


    public Store findStoreByUuid(String storeUuid){

        return storeMapper.selectByUuid(storeUuid);
    }
}
