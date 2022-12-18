package com.example.nft.dao;

import com.example.nft.entity.Consumer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ConsumerMapper {
    // 增加顾客
    boolean insert (Consumer consumer);

    // 设置顾客状态
    boolean statusByPhone(Integer consumerStatus ,String consumerPhone);

    // 更新顾客信息
    boolean updateByPhone(Consumer consumer);

    // 修改用户密码
    boolean updatePasswordByPhone(String consumerPhone, String consumerPassword);

    // 查询单个顾客信息by手机号
    Consumer selectByPhone(String consumerPhone);

    // 查询单个用户信息by uuid
    Consumer selectByUuid(String consumerUuid);

    // 查询20个顾客信息
    List<Consumer> selectMany(Integer currentPage);

    // 查询总数
    int count();

    // 查询被删除的顾客信息
    // 查询已删除单个顾客信息by手机号
    Consumer selectByPhoneBan(String consumerPhone);

    // 查询已删除20个顾客信息
    List<Consumer> selectBanMany(Integer currentPage);

}
