package com.example.nft.service.impl;


import com.example.nft.dao.BalanceLogMapper;
import com.example.nft.entity.BalanceLog;
import com.example.nft.service.BalanceLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BalanceLogServiceImpl implements BalanceLogService {



    @Resource
    private BalanceLogMapper balanceLogMapper;

    /**
     *根据参数情况查找所有的的记录
     * @param balanceLogPhone
     * @param startTime
     * @param endTime
     * @param currentPage
     * @return
     */
    @Override
    public HashMap<Object, Object> findMany(String balanceLogPhone, String balanceLogStore, String startTime, String endTime, Integer currentPage) {

        HashMap<Object, Object> map = new HashMap<>();

        List<BalanceLog> info = balanceLogMapper.selectMany( balanceLogPhone, balanceLogStore, startTime, endTime, (currentPage-1)*20);
        Integer sum = balanceLogMapper.count(balanceLogPhone,balanceLogStore, startTime, endTime);
        map.put("info", info);
        map.put("sum", sum);
        return map;
    }

    /**
     * 操作记录插入数据库
     * @param balanceLogPhone
     * @param
     * @param balanceLogService1
     * @param balanceLogService2
     * @param balanceLogService3
     * @param balanceLogService4
     * @param balanceLogService5
     * @param
     * @return
     */
    @Override
    public Boolean insertLog(String balanceLogPhone, String balanceLogStore , Integer balanceLogMoney, Integer balanceLogService1, Integer balanceLogService2, Integer balanceLogService3, Integer balanceLogService4, Integer balanceLogService5) {

        return null;
    }


    /**
     *  获取日期范围内充值的金额总数，也就是balanceLogMoney > 0的
     * @param startTime
     * @param endTime
     * @return
     */
    public Map findDayAddSum(String balanceLogStore, String startTime, String endTime){
        ArrayList<Map> dbRes = balanceLogMapper.moneyDayAddSum(balanceLogStore, startTime,endTime);
        List<Object> balanceLogMoney = new ArrayList<>();
        List<Object> createTime = new ArrayList<>();
        HashMap<Object, Object> map = new HashMap<>();
        for(int i = 0; i < dbRes.size(); i++){
            balanceLogMoney.add(dbRes.get(i).get("balanceLogMoney"));
            createTime.add(dbRes.get(i).get("createTime"));
        }
        map.put("balanceLogMoney",balanceLogMoney);
        map.put("createTime",createTime);
        return map;
    }

    /**
     *  获取日期范围内充值的金额总数，也就是balanceLogMoney < 0的
     * @param startTime
     * @param endTime
     * @return
     */
    public Map findDayReduceSum(String balanceLogStore, String startTime, String endTime){
        System.out.println(balanceLogMapper.moneyDayReduceSum(startTime,endTime));
        ArrayList<Map> dbRes = balanceLogMapper.moneyDayReduceSum(startTime,endTime);
        List<Object> balanceLogMoney = new ArrayList<>();
        List<Object> createTime = new ArrayList<>();
        HashMap<Object, Object> map = new HashMap<>();
        for(int i = 0; i < dbRes.size(); i++){
            balanceLogMoney.add(dbRes.get(i).get("balanceLogMoney"));
            createTime.add(dbRes.get(i).get("createTime"));
        }
        map.put("balanceLogMoney",balanceLogMoney);
        map.put("createTime",createTime);
        return map;
    }


    /**
     *  获取日期范围内充值的服务总数，也就是balanceLogService > 0的
     * @param startTime
     * @param endTime
     * @return
     */
    public Map findServiceDayAddSum(String balanceLogStore, String startTime, String endTime){
        System.out.println(balanceLogMapper.serviceDayAddSum(startTime,endTime));
        ArrayList<Map> dbRes = balanceLogMapper.serviceDayAddSum(startTime,endTime);
        List<Object> balanceLogService1 = new ArrayList<>();
        List<Object> balanceLogService2 = new ArrayList<>();
        List<Object> balanceLogService3 = new ArrayList<>();
        List<Object> balanceLogService4 = new ArrayList<>();
        List<Object> balanceLogService5 = new ArrayList<>();
        List<Object> createTime = new ArrayList<>();
        HashMap<Object, Object> map = new HashMap<>();
        for(int i = 0; i < dbRes.size(); i++){
            balanceLogService1.add(dbRes.get(i).get("balanceLogService1"));
            balanceLogService2.add(dbRes.get(i).get("balanceLogService2"));
            balanceLogService3.add(dbRes.get(i).get("balanceLogService3"));
            balanceLogService4.add(dbRes.get(i).get("balanceLogService4"));
            balanceLogService5.add(dbRes.get(i).get("balanceLogService5"));
            createTime.add(dbRes.get(i).get("createTime"));
        }
        map.put("balanceLogService1",balanceLogService1);
        map.put("balanceLogService2",balanceLogService2);
        map.put("balanceLogService3",balanceLogService3);
        map.put("balanceLogService4",balanceLogService4);
        map.put("balanceLogService5",balanceLogService5);
        map.put("createTime",createTime);
        return map;
    }


    /**
     *  获取日期范围内充值的服务总数，也就是balanceLogService 《》 0的
     * @param startTime
     * @param endTime
     * @return
     */
    public Map findServiceDayReduceSum(String balanceLogStore, String startTime, String endTime){
        System.out.println(balanceLogMapper.serviceDayReduceSum(startTime,endTime));
        ArrayList<Map> dbRes = balanceLogMapper.serviceDayReduceSum(startTime,endTime);
        List<Object> balanceLogService1 = new ArrayList<>();
        List<Object> balanceLogService2 = new ArrayList<>();
        List<Object> balanceLogService3 = new ArrayList<>();
        List<Object> balanceLogService4 = new ArrayList<>();
        List<Object> balanceLogService5 = new ArrayList<>();
        List<Object> createTime = new ArrayList<>();
        HashMap<Object, Object> map = new HashMap<>();
        for(int i = 0; i < dbRes.size(); i++){
            balanceLogService1.add(dbRes.get(i).get("balanceLogService1"));
            balanceLogService2.add(dbRes.get(i).get("balanceLogService2"));
            balanceLogService3.add(dbRes.get(i).get("balanceLogService3"));
            balanceLogService4.add(dbRes.get(i).get("balanceLogService4"));
            balanceLogService5.add(dbRes.get(i).get("balanceLogService5"));
            createTime.add(dbRes.get(i).get("createTime"));
        }
        map.put("balanceLogService1",balanceLogService1);
        map.put("balanceLogService2",balanceLogService2);
        map.put("balanceLogService3",balanceLogService3);
        map.put("balanceLogService4",balanceLogService4);
        map.put("balanceLogService5",balanceLogService5);
        map.put("createTime",createTime);
        return map;
    }

}
