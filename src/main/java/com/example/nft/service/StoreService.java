package com.example.nft.service;


import com.example.nft.entity.Consumer;
import com.example.nft.entity.Store;
import org.apache.catalina.session.StoreBase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoreService {

    Boolean add(Store store);


    List<Store> findAll();

    /**
     * 根据店铺名模糊查询
     * @param storeName
     * @return
     */
    List<Store> findStoreByName(String storeName);

}
