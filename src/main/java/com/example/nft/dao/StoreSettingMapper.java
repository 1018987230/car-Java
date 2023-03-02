package com.example.nft.dao;

import com.example.nft.entity.StoreSetting;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author wangyixiong
 * @Date 2023/2/23 上午12:31
 * @PackageName:com.example.nft.dao
 * @ClassName: SettingMapper
 * @Description: TODO
 * @Version 1.0
 */
@Mapper
public interface StoreSettingMapper {

    /**
     * 新增店铺配置
     * @param
     * @return
     */
    Integer insert(String storeUuid);

    /**
     * 获取店铺相关配置
     * @param storeUuid
     * @return
     */
    StoreSetting select(String storeUuid);


    /**
     * 改店铺配置
     * @param storeSetting
     * @return
     */
    Integer updateSetting(StoreSetting storeSetting);
}
