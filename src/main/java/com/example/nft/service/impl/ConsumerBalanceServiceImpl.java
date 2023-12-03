package com.example.nft.service.impl;


import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.dao.BalanceLogMapper;
import com.example.nft.dao.ConsumerBalanceMapper;
import com.example.nft.dao.ConsumerMapper;
import com.example.nft.dao.NoticeMapper;
import com.example.nft.entity.Consumer;
import com.example.nft.entity.ConsumerBalance;
import com.example.nft.entity.Notice;
import com.example.nft.entity.StoreSetting;
import com.example.nft.service.ConsumerBalanceService;
import com.example.nft.service.ConsumerService;
import com.example.nft.service.StoreSettingService;
import com.example.nft.service.ex.BalanceException;
import com.example.nft.service.ex.InsertException;
import com.example.nft.service.ex.SelectException;
import com.example.nft.service.ex.UpdateException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ConsumerBalanceServiceImpl implements ConsumerBalanceService {

    @Resource
    private ConsumerBalanceMapper consumerBalanceMapper;


    @Resource
    private BalanceLogMapper balanceLogMapper;

    @Resource
    private ConsumerMapper consumerMapper;

    @Resource
    private NoticeMapper noticeMapper;

    @Resource
    private StoreSettingService storeSettingService;

    public static final Integer NORMAL_BALANCE_STATUS = 0;

    public static final Integer ABNORMAL_BALANCE_STATUS = 1;

    /**
     * 账户余额全部变动
     * @param balanceOwnerPhone
     * @param costMoney
     * @param costService1
     * @param costService2
     * @param costService4
     * @param costService5
     * @return
     */
    @Override
    public String change(String balanceOwnerPhone, String storeUuid,Integer costMoney, Integer costService1, Integer costService2,
                          Integer costService3, Integer costService4, Integer costService5) {
        ConsumerBalance db_res = consumerBalanceMapper.selectByPhoneStore(storeUuid, balanceOwnerPhone,NORMAL_BALANCE_STATUS);
        // 查找用户是否存在
        if(db_res == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        // 处理金额
        int balanceMoney = db_res.getBalanceMoney() + costMoney;
        // 处理消费服务
        int balanceService1 = db_res.getBalanceService1() + costService1;
        int balanceService2 = db_res.getBalanceService2() + costService2;
        int balanceService3 = db_res.getBalanceService3() + costService3;
        int balanceService4 = db_res.getBalanceService4() + costService4;
        int balanceService5 = db_res.getBalanceService5() + costService5;

        // 余额不足
        if(balanceMoney < 0  ||balanceService1 < 0 || balanceService2 < 0 || balanceService3 < 0 || balanceService4 < 0 || balanceService5 < 0 ){
            throw new BalanceException(ServiceResultEnum.BALANCE_NOT_ENOUGH.getResult());
        }

        // 更新金额
        if(!consumerBalanceMapper.update(balanceOwnerPhone,storeUuid, balanceMoney,balanceService1,
                                        balanceService2, balanceService3,balanceService4,balanceService5)){
            throw new UpdateException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }

        // 保存更新记录
        if(!balanceLogMapper.insertLog(balanceOwnerPhone,storeUuid,costMoney,costService1,costService2,
                costService3,costService4,costService5)){
            throw new InsertException(ServiceResultEnum.DB_INSERT_LOG_ERROR.getResult());
        }

        Consumer consumer = consumerMapper.selectByPhone(balanceOwnerPhone);
        StoreSetting storeSetting = storeSettingService.findByStore(storeUuid);
        String content = "余额："+costMoney+"元，" +
                storeSetting.getServiceName1() +": "+ balanceService1 +"次，" +
                storeSetting.getServiceName2() +": "+ balanceService2 +"次，" +
                storeSetting.getServiceName3() +": "+ balanceService3 +"次，" +
                storeSetting.getServiceName4() +": "+ balanceService4 +"次，" +
                storeSetting.getServiceName5() +": "+ balanceService5 +"次。";
        Notice notice = new Notice();
        notice.setNoticeUuid("N" + (new Date().getTime() + (int)((Math.random()*9+1)*100000) + "").substring(2));
        notice.setNoticeFrom(storeUuid);
        notice.setNoticeTo(consumer.getConsumerUuid());
        notice.setNoticeTitle("余额消息");
        notice.setNoticeContent("手机号："+consumer.getConsumerPhone()+"的账户发生变化。"+ content);
        noticeMapper.insertNotice(notice);

        return ServiceResultEnum.SUCCESS.getResult();
    }

    /**
     *
     * @param
     * @param costMoney  花费的金额
     * @return
     */
    @Override
    public ConsumerBalance moneyChange(String balanceOwnerPhone, String storeUuid,Integer costMoney) {
        System.out.println(balanceOwnerPhone);
        ConsumerBalance db_res = consumerBalanceMapper.selectByPhoneStore(storeUuid, balanceOwnerPhone,NORMAL_BALANCE_STATUS);
        // 查找用户是否存在
        if(db_res == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        // 处理金额
        int balanceMoney = db_res.getBalanceMoney() + costMoney;

        // 余额不足
        if(balanceMoney < 0){
            throw new BalanceException(ServiceResultEnum.BALANCE_NOT_ENOUGH.getResult());
        }

        // 更新金额
        if(!consumerBalanceMapper.updateMoneyByPhone(balanceOwnerPhone,storeUuid, balanceMoney)){
            throw new UpdateException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }

        // 保存更新记录
        if(!balanceLogMapper.insertLog(balanceOwnerPhone,storeUuid,costMoney,0,0,
                0,0,0)){
            throw new InsertException(ServiceResultEnum.DB_INSERT_LOG_ERROR.getResult());
        }

        return consumerBalanceMapper.selectByPhoneStore(storeUuid, balanceOwnerPhone,NORMAL_BALANCE_STATUS);
    }

    @Override
    public ConsumerBalance serviceChange(String balanceOwnerPhone,String storeUuid, Integer costService1, Integer costService2,
                                Integer costService3, Integer costService4, Integer costService5) {
        // 查找用户是否存在
        ConsumerBalance db_res = consumerBalanceMapper.selectByPhoneStore(storeUuid, balanceOwnerPhone,NORMAL_BALANCE_STATUS);
        if(db_res == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }

        // 处理消费服务
        int balanceService1 = db_res.getBalanceService1() + costService1;
        int balanceService2 = db_res.getBalanceService2() + costService2;
        int balanceService3 = db_res.getBalanceService3() + costService3;
        int balanceService4 = db_res.getBalanceService4() + costService4;
        int balanceService5 = db_res.getBalanceService5() + costService5;

        // 余额不足
        if(balanceService1 < 0 || balanceService2 < 0 || balanceService3 < 0 && balanceService4 < 0 || balanceService5 < 0 ){
            throw new BalanceException(ServiceResultEnum.BALANCE_NOT_ENOUGH.getResult());
        }

        // 更新
        if(!consumerBalanceMapper.updateServiceByPhone(balanceOwnerPhone,storeUuid, balanceService1,balanceService2,
                balanceService3,balanceService4,balanceService5)){
            throw new UpdateException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }

        // 保存更新记录
        if(!balanceLogMapper.insertLog(balanceOwnerPhone,storeUuid,0,costService1,costService2,
                costService3,costService4,costService5)){
            throw new InsertException(ServiceResultEnum.DB_INSERT_LOG_ERROR.getResult());
        }
        return consumerBalanceMapper.selectByPhoneStore(storeUuid, balanceOwnerPhone,NORMAL_BALANCE_STATUS);
    }

    /**
     * 用户和店铺连接或断开时调用
     * @param consumerUuid
     * @param storeUuid
     * @param balanceStatus
     * @return
     */
    @Override
    public String statusChange(String consumerUuid, String storeUuid, Integer balanceStatus) {
        // 用户uuid转为手机号
        String consumerPhone = consumerMapper.selectByUuid(consumerUuid).getConsumerPhone();
        if(!consumerBalanceMapper.updateStatus(consumerPhone, storeUuid, balanceStatus)){
            throw new UpdateException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }

        return ServiceResultEnum.SUCCESS.getResult();
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<ConsumerBalance> findOne(String balanceOwnerPhone, String storeUuid){
        ConsumerBalance dbRes = consumerBalanceMapper.selectByPhoneStore(storeUuid, balanceOwnerPhone,NORMAL_BALANCE_STATUS);
        if(dbRes == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        ArrayList<ConsumerBalance> list = new ArrayList<>();
        list.add(dbRes);
        return list;
    }

    @Override
    public HashMap<Object, Object> findMany(String storeUuid ,Integer currentPage){
        HashMap<Object, Object> map = new HashMap<>();
        List<ConsumerBalance> info = consumerBalanceMapper.selectMany(storeUuid,(currentPage-1)*20);
        Integer sum = consumerBalanceMapper.count(storeUuid);
        map.put("info",info);
        map.put("sum", sum);

        return map;
    }


}
