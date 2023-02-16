package com.example.nft.dao;

import com.example.nft.entity.ConsumerBalance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConsumerBalanceMapper {

    Boolean insertBalance(String storeUuid ,String balanceOwnerPhone);

    // 设置余额状态
    Boolean updateStatus(String balanceOwnerPhone, String storeUuid,Integer balanceStatus);

    List<ConsumerBalance> selectMany(String storeUuid , Integer currentPage);

    Integer count(String storeUuid);

    ConsumerBalance selectByPhoneStore(String storeUuid, String consumerPhone, Integer balanceStatus);

    Boolean update(String balanceOwnerPhone, String storeUuid, Integer balanceMoney, Integer balanceService1, Integer balanceService2,
                   Integer balanceService3, Integer balanceService4, Integer balanceService5);

    Boolean updateMoneyByPhone (String balanceOwnerPhone, String storeUuid , Integer balanceMoney);

    Boolean updateServiceByPhone (String balanceOwnerPhone,String storeUuid ,Integer balanceService1, Integer balanceService2,
                                  Integer balanceService3, Integer balanceService4, Integer balanceService5);

}
