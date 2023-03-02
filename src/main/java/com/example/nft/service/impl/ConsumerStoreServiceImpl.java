package com.example.nft.service.impl;


import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.dao.*;
import com.example.nft.entity.Consumer;
import com.example.nft.entity.Notice;
import com.example.nft.entity.Store;
import com.example.nft.service.ConsumerStoreService;
import com.example.nft.service.ex.DuplicateException;
import com.example.nft.service.ex.InsertException;
import com.example.nft.service.ex.SelectException;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ConsumerStoreServiceImpl implements ConsumerStoreService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String NOTICE_TITLE = "人员消息";

    public static final Integer NORMAL_BALANCE_STATUS = 0;

    public static final Integer ABNORMAL_BALANCE_STATUS = 1;

    @Resource
    private ConsumerStoreMapper consumerStoreMapper;

    @Resource
    private ConsumerMapper consumerMapper;

    @Resource
    private StoreMapper storeMapper;

    @Resource
    private NoticeMapper noticeMapper;

    @Resource
    private ConsumerBalanceMapper consumerBalanceMapper;


    /**
     *  店铺和顾客通过各自的id建立联系
     * @return
     */
    @Transactional
    @Override
    public String ConsumerAndStore(String consumerUuid, String storeUuid) {

        // 判断consumerId和storeId是否存在，如果不存在，不符合要求，抛异常
//        if(consumerId == "" || storeId == ""){
//
//        }

        System.out.println(consumerStoreMapper.selectConsumerStore(consumerUuid, storeUuid));
        // 如果已经存在关系，查询结果不为空，抛异常
        if (consumerStoreMapper.selectConsumerStore(consumerUuid, storeUuid) != null){
            throw new DuplicateException(ServiceResultEnum.DB_EXIST.getResult());
        }

        // 插入操作
        if (consumerStoreMapper.consumerAndStore(consumerUuid, storeUuid) != 1){
            throw new InsertException("顾客店铺关系数据插入失败！");
        }

        // 查找该id用户信息
        Consumer consumer = consumerMapper.selectByUuid(consumerUuid);
        // 查找该storeId的店铺信息
        Store store = storeMapper.selectByUuid(storeUuid);

        // 发起消息通知: 用户加入店铺
        Notice notice = new Notice();
        String noticeUuid = "N" + (new Date().getTime() + (int)((Math.random()*9+1)*100000) + "").substring(2);
        notice.setNoticeUuid(noticeUuid);
        notice.setNoticeFrom(consumerUuid);
        notice.setNoticeTo(storeUuid);
        notice.setNoticeTitle(NOTICE_TITLE);
        notice.setNoticeContent("用户 "+ consumer.getConsumerName() + consumer.getConsumerUuid() + " 加入店铺 " + store.getStoreName()+ store.getStoreUuid() + "。");

        if(noticeMapper.insertNotice(notice) == 0){
            throw new InsertException(ServiceResultEnum.ORDER_INSERT_ERROR.getResult());
        }

        // 用户加入店铺时，需要创建账户余额

        // 判断店铺中是否已经存在账户，查询客户是否加入过店铺，状态为0和1的都要查询，有一个不为空就不插入
        if(consumerBalanceMapper.selectByPhoneStore(storeUuid, consumer.getConsumerPhone(),NORMAL_BALANCE_STATUS) == null && consumerBalanceMapper.selectByPhoneStore(storeUuid, consumer.getConsumerPhone(),ABNORMAL_BALANCE_STATUS) == null){
            // 插入账户
            if(!consumerBalanceMapper.insertBalance(storeUuid, consumer.getConsumerPhone())){
                throw new InsertException(ServiceResultEnum.BALANCE_INSERT_ERROR.getResult());
            }
        }
        // 改变状态，置为0
        consumerBalanceMapper.updateStatus(consumer.getConsumerPhone(),storeUuid, NORMAL_BALANCE_STATUS);

        logger.info("店铺和用户建立联系：" + "consumerId: " + consumerUuid + "storeId: " + storeUuid );
        return  ServiceResultEnum.SUCCESS.getResult();
    }


    /**
     * 删除顾客和店铺之间的关系
     * @param
     * @param
     * @return
     */
    @Override
    public String removeConsumerStore(String consumerUuid, String storeUuid) {
        System.out.println(consumerUuid);
        // 如果不存在关系，查询结果返回null，抛出异常
        if (consumerStoreMapper.selectConsumerStore(consumerUuid, storeUuid) == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }

        Integer dbRes = consumerStoreMapper.deleteConsumerStore(consumerUuid, storeUuid);
        if(dbRes != 1){
            return ServiceResultEnum.ERROR.getResult();
        }

        // 查找该consumerId用户信息
        Consumer consumer = consumerMapper.selectByUuid(consumerUuid);
        // 查找该storeId的店铺信息
        Store store = storeMapper.selectByUuid(storeUuid);

        // 发起消息通知: 用户退出店铺
        Notice notice = new Notice();
        String noticeUuid = "N" + (new Date().getTime() + (int)((Math.random()*9+1)*100000) + "").substring(2);
        notice.setNoticeUuid(noticeUuid);
        notice.setNoticeFrom(consumerUuid);
        notice.setNoticeTo(storeUuid);
        notice.setNoticeTitle(NOTICE_TITLE);
        notice.setNoticeContent("用户 " + consumer.getConsumerName() + consumer.getConsumerUuid() + " 退出当前店铺 " + store.getStoreName()+store.getStoreUuid() + "。");

        if(noticeMapper.insertNotice(notice) == 0){
            throw new InsertException(ServiceResultEnum.ORDER_INSERT_ERROR.getResult());
        }


        logger.info("店铺和用户断开联系：" + "consumerId: " + consumerUuid + "storeId: " + storeUuid );
        return ServiceResultEnum.SUCCESS.getResult();
    }


    /**
     * 通过顾客id查询加入的店铺
     * @param
     * @return
     */
    @Override
    public HashMap<String, Object> findStoreByConsumerUuid(String consumerUuid) {
        HashMap<String, Object> map = new HashMap<>();
        List<Store> info = consumerStoreMapper.selectStoreByConsumerUuid(consumerUuid);
        Integer sum = consumerStoreMapper.selectStoreCountByConsumerUuid(consumerUuid);
        map.put("info", info);
        map.put("sum", sum);
        return map;
    }

    /**
     * 通过店铺id查询顾客
     * @param
     * @return
     */
    @Override
    public HashMap<String, Object> findConsumerByStoreUuid(String storeUuid, Integer currentPage) {
        HashMap<String, Object> map = new HashMap<>();
        List<Consumer> info = consumerStoreMapper.selectConsumerByStoreUuid(storeUuid, (currentPage - 1)*20);
        Integer sum = consumerStoreMapper.selectConsumerCountByStoreUuid(storeUuid);
        map.put("info", info);
        map.put("sum", sum);
        return map;
    }

}
