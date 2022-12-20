package com.example.nft.service;


import com.example.nft.entity.BalanceLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface BalanceLogService {

    //查找所有的记录，根据参数情况，在mybatis动态sql中实现
    HashMap<Object, Object> findMany(String balanceLogPhone, String balanceLogStore, String startTime, String endTime, Integer currentPage);

    Boolean insertLog(String balanceLogPhone,String balanceLogStore, Integer balance_log_money,
                      Integer balanceLogService1,Integer balanceLogService2,Integer balanceLogService3,
                      Integer balanceLogService4,Integer balanceLogService5);

    Map findDayAddSum(String balanceLogStore, String startTime, String endTime);

    Map findDayReduceSum(String balanceLogStore, String startTime, String endTime);

    Map findServiceDayAddSum(String balanceLogStore, String startTime, String endTime);

    Map findServiceDayReduceSum(String balanceLogStore, String startTime, String endTime);
}
