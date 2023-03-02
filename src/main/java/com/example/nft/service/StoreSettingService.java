package com.example.nft.service;

import com.example.nft.entity.StoreSetting;
import org.springframework.stereotype.Service;

/**
 * @Author wangyixiong
 * @Date 2023/2/23 下午4:30
 * @PackageName:com.example.nft.service
 * @ClassName: StoreSettingService
 * @Description: TODO
 * @Version 1.0
 */
@Service
public interface StoreSettingService {

    /**
     * 添加店铺配置
     * @param
     * @return
     */
    String add(String storeUuid);


    /**
     * 获取店铺配置
     * @param storeUuid
     * @return
     */
    StoreSetting findByStore(String storeUuid);


    /**
     * 修改配置
     * @param storeSetting
     * @return
     */
    String change(StoreSetting storeSetting);
}
