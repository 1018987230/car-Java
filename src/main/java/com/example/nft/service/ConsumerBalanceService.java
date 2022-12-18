package com.example.nft.service;


import com.example.nft.entity.ConsumerBalance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public interface ConsumerBalanceService {

    String change(String balanceOwnerPhone, String storeUuid,Integer costMoney, Integer balanceService1, Integer balanceService2,
                   Integer balanceService3, Integer balanceService4, Integer balanceService5);

    ConsumerBalance moneyChange(String balanceOwnerPhone, String storeUuid, Integer costMoney);

    ConsumerBalance serviceChange(String balanceOwnerPhone, String storeUuid,Integer balanceService1, Integer balanceService2,
                         Integer balanceService3, Integer balanceService4, Integer balanceService5);

    ArrayList<Object> findOne(String balanceOwnerPhone, String storeUuid);

    HashMap<Object, Object> findMany(String storeUuid , Integer currentPage);


}
