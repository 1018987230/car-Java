package com.example.nft.dao;

import com.example.nft.entity.OpenidPhone;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author wangyixiong
 * @Date 2023/2/26 上午12:31
 * @PackageName:com.example.nft.dao
 * @ClassName: OpenidPhoneMapper
 * @Description: TODO
 * @Version 1.0
 */
@Mapper
public interface OpenidPhoneMapper {

    /**
     * 插入表信息
     * @param openidPhone
     * @return
     */
    Integer insert(OpenidPhone openidPhone);

    /**
     * 根据openid查询微信用户是否注册
     * @param openid
     * @return
     */
    OpenidPhone selectByOpenid(String openid);
}
