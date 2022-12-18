package com.example.nft.dao;


import com.example.nft.entity.BalanceLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface BalanceLogMapper {

    List<BalanceLog> selectMany(String balanceLogPhone, String balanceLogStore, String startTime, String endTime, Integer currentPage);

    Integer count(String balanceLogPhone, String balanceLogStore, String startTime, String endTime);


    Boolean insertLog(String balanceLogPhone, String balanceLogStore, Integer balanceLogMoney,
                      Integer balanceLogService1,Integer balanceLogService2,Integer balanceLogService3,
                      Integer balanceLogService4,Integer balanceLogService5);

    ArrayList<Map> moneyDayAddSum(String startTime, String endTime);

    ArrayList<Map> moneyDayReduceSum(String startTime, String endTime);

    ArrayList<Map> serviceDayAddSum(String startTime, String endTime);

    ArrayList<Map> serviceDayReduceSum(String startTime, String endTime);

}
