package com.example.nft.service;


import com.example.nft.entity.ConsumerBalance;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public interface ConsumerBalanceService {



    String change(String balanceOwnerPhone, String storeUuid,Integer costMoney, Integer balanceService1, Integer balanceService2,
                   Integer balanceService3, Integer balanceService4, Integer balanceService5);

    /**
     * 改变用户的金额
     * @param balanceOwnerPhone
     * @param storeUuid
     * @param costMoney
     * @return
     */
    ConsumerBalance moneyChange(String balanceOwnerPhone, String storeUuid, Integer costMoney);

    /**
     * 改变用户所拥有的服务
     * @param balanceOwnerPhone
     * @param storeUuid
     * @param balanceService1
     * @param balanceService2
     * @param balanceService3
     * @param balanceService4
     * @param balanceService5
     * @return
     */
    ConsumerBalance serviceChange(String balanceOwnerPhone, String storeUuid,Integer balanceService1, Integer balanceService2,
                         Integer balanceService3, Integer balanceService4, Integer balanceService5);

    /**
     * 改变账户的状态
     * @param balanceOwnerPhone
     * @param storeUuid
     * @param balanceStatus
     * @return
     */
    String statusChange(String balanceOwnerPhone, String storeUuid, Integer balanceStatus);

    /**
     * 查询单个账户
     * @param balanceOwnerPhone
     * @param storeUuid
     * @return
     */
    ArrayList<ConsumerBalance> findOne(String balanceOwnerPhone, String storeUuid);

    HashMap<Object, Object> findMany(String storeUuid , Integer currentPage);




}
