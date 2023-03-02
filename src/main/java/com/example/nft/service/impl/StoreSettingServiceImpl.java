package com.example.nft.service.impl;

import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.dao.StoreSettingMapper;
import com.example.nft.entity.StoreSetting;
import com.example.nft.service.StoreSettingService;
import com.example.nft.service.ex.InsertException;
import com.example.nft.service.ex.SelectException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author wangyixiong
 * @Date 2023/2/23 下午4:30
 * @PackageName:com.example.nft.service.impl
 * @ClassName: StoreSettingServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class StoreSettingServiceImpl implements StoreSettingService {

    @Resource
    private StoreSettingMapper storeSettingMapper;

    @Override
    public String add(String storeUuid) {
        if(storeSettingMapper.insert(storeUuid) !=1){
            throw new InsertException("修改店铺配置失败");
        };
        return ServiceResultEnum.SUCCESS.getResult();
    }


    @Override
    public StoreSetting findByStore(String storeUuid) {
        StoreSetting dbRes = storeSettingMapper.select(storeUuid);
        if(dbRes == null){
            throw new SelectException("查询不到该店铺配置");
        }
        return dbRes;
    }

    @Override
    public String change(StoreSetting storeSetting) {
        storeSettingMapper.updateSetting(storeSetting);
        return ServiceResultEnum.SUCCESS.getResult();
    }
}
