package com.example.nft.service.impl;

import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.dao.*;
import com.example.nft.entity.*;
import com.example.nft.service.OrderService;
import com.example.nft.service.ex.InsertException;
import com.example.nft.service.ex.SelectException;
import com.example.nft.service.ex.UpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2022/10/23 上午2:07
 * @PackageName:com.example.nft.service.impl
 * @ClassName: OrderServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String NOTICE_TITLE = "订单消息";

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private NoticeMapper noticeMapper;

    @Resource
    private StoreMapper storeMapper;

    @Resource
    private ConsumerMapper consumerMapper;

    @Resource
    private ConsumerStoreMapper consumerStoreMapper;

    @Resource
    private CarMapper carMapper;

    /**
     * 订单生成
     * @param
     * @param
     * @param orderCarNumber
     * @param orderService
     * @param orderNote
     * @param orderPreordainTime
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String addOrder(String orderConsumerUuid, String orderStoreUuid, String orderCarNumber, String orderService, String orderNote, String orderPreordainTime) {

        // 根据uuid查询店铺消息
        Store store = storeMapper.selectByUuid(orderStoreUuid);

        // 生成订单
        // 生成伪随机六位数 + 当前时间戳
        String orderUuid = "O" + (new Date().getTime() + (int)((Math.random()*9+1)*100000) + "").substring(2);;
        Order order = new Order();
        order.setOrderUuid(orderUuid);
        order.setOrderConsumerUuid(orderConsumerUuid);
        order.setOrderStoreUuid(orderStoreUuid);
        order.setOrderCarNumber(orderCarNumber);
        order.setOrderService(orderService);
        order.setOrderNote(orderNote);
        order.setOrderPreordainTime(orderPreordainTime);
        if(orderMapper.insertOrder(order) != 1){
            throw new InsertException(ServiceResultEnum.DB_INSERT_ERROR.getResult());
        }

        // 根据orderConsumerId找出用户信息
        Consumer consumer = consumerMapper.selectByUuid(orderConsumerUuid);

        // 生成通知，给请求的店铺发送通知信息
        Notice notice = new Notice();
        String noticeUuid = "N" + (new Date().getTime() + (int)((Math.random()*9+1)*100000) + "").substring(2);
        notice.setNoticeUuid(noticeUuid);
        notice.setNoticeFrom(orderConsumerUuid);
        notice.setNoticeTo(orderStoreUuid);
        notice.setNoticeTitle(NOTICE_TITLE);
        notice.setNoticeContent("手机号为：" + consumer.getConsumerPhone() + "的用户"+ "发起订单！请即时查看！订单号为：" + orderUuid + "。");

        if(noticeMapper.insertNotice(notice) == 0){
            throw new InsertException(ServiceResultEnum.ORDER_INSERT_ERROR.getResult());
        }

        logger.info("orderUuid: " + order.getOrderUuid() + " create by consumerUuid: " + order.getOrderConsumerUuid() + " to storeUuid: " + order.getOrderStoreUuid());
        return ServiceResultEnum.SUCCESS.getResult();
    }


    /**
     *
     * 如果传过来的有storeUuid则是店铺操作，要进行判断联系
     * @param carNumber
     * @param storeUuid
     * @param currentPage
     * @return
     */
    @Override
    public List<Order> findByCarNumber(String carNumber,String storeUuid, Integer currentPage) {

        // 如果传进来的有店铺，则是店铺操作，要判断用户和店铺是否存在关系
        if(storeUuid != null && !storeUuid.equals("")){
            // 根据车牌号查询车主手机号
            Car car = carMapper.selectByNumber(carNumber);
            if(car == null){
                throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
            }
            // 根据车主手机号查询车主全部信息
            Consumer consumer = consumerMapper.selectByPhone(car.getCarOwnerPhone());
            if(consumer == null){
                throw new SelectException(ServiceResultEnum.DB_PHONE_NOT_EXIST.getResult());
            }
            // 根据车主信息中的uuid判断是否存在联系
            if (consumerStoreMapper.selectConsumerStore(consumer.getConsumerUuid(), storeUuid) == null){
                throw new SelectException(ServiceResultEnum.DB_CONSUMER_NOT_JOIN.getResult());
            }
        }

        List<Order> dbRes = orderMapper.selectByCarNumber(carNumber, storeUuid,( currentPage-1)*20);
        return dbRes;
    }

    /**
     *  修改订单状态
     * @param orderUuid
     * @param orderStatus
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String changeStatus(String orderUuid, Integer orderStatus) {

        // 1. 判断订单是否存在，若不存在抛出异常
        Order order = orderMapper.selectByUuid(orderUuid);
        if(order == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }

        // 2. 获取订单当前状态，若订单状态为6，则不可进行操作
        if(order.getOrderStatus().equals("6")){
            throw new UpdateException(ServiceResultEnum.ORDER_PROHIBIT_MODIFIED.getResult());
        }

        // 4. 对订单进行更新
        Integer dbRes = orderMapper.updateStatus(orderUuid, orderStatus);
        if(dbRes == 0){
            throw new UpdateException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }

        // 根据orderConsumerId找出用户信息
        Consumer consumer = consumerMapper.selectByUuid(order.getOrderConsumerUuid());
        Store store = storeMapper.selectByUuid(order.getOrderStoreUuid());

        System.out.println(consumer);
        // 5. 生成通知，给请求的店铺发送通知信息
        Notice notice = new Notice();
        String noticeUuid = "N" + (new Date().getTime() + (int)((Math.random()*9+1)*100000) + "").substring(2);
        notice.setNoticeUuid(noticeUuid);
        notice.setNoticeFrom(order.getOrderConsumerUuid());
        notice.setNoticeTo(order.getOrderStoreUuid());
//        notice.setNoticeFrom(consumer.getConsumerPhone());
//        notice.setNoticeTo(store.getStoreName());
        notice.setNoticeTitle(NOTICE_TITLE);
        String text = numToText(orderStatus);
        notice.setNoticeContent("订单号：" + orderUuid + " 状态发生改变，请及时查收。当前状态："+ text);

        if(noticeMapper.insertNotice(notice) == 0){
            throw new InsertException(ServiceResultEnum.ORDER_INSERT_ERROR.getResult());
        }

        // 6. 记录通知生成  和  订单状态改变
        logger.info(notice.getNoticeUuid() + " inserted successfully");

        logger.info("orderUuid: "+ orderUuid + " status is modified to: " + orderStatus);
        return ServiceResultEnum.SUCCESS.getResult();
    }


    /**
     * 通过订单号查询订单
     * @param orderUuid
     * @return
     */
    @Override
    public List<Order> findByUuid(String orderUuid) {

        Order dbRes = orderMapper.selectByUuid(orderUuid);
        if (dbRes == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        List<Order> orders = new ArrayList<>();
        orders.add(dbRes);
        return orders;
    }


    /**
     * 如果传过来的有storeUuid则是店铺操作，要进行判断联系
     * @param orderConsumerPhone
     * @param storeUuid
     * @param currentPage
     * @return
     */
    @Transactional
    @Override
    public List<Order> findByConsumerPhone(String orderConsumerPhone,String storeUuid, Integer currentPage) {

        Consumer consumer = consumerMapper.selectByPhone(orderConsumerPhone);

        if(consumer == null){
            throw new SelectException(ServiceResultEnum.DB_PHONE_NOT_EXIST.getResult());
        }

        // 如果传进来的有店铺，则是店铺操作，要判断用户和店铺是否存在关系
        if(storeUuid != null && !storeUuid.equals("")){
            if (consumerStoreMapper.selectConsumerStore(consumer.getConsumerUuid(), storeUuid) == null){
                throw new SelectException(ServiceResultEnum.DB_CONSUMER_NOT_JOIN.getResult());
            }
        }

        List<Order> dbRes = orderMapper.selectByConsumerUuid(consumer.getConsumerUuid(), storeUuid,(currentPage-1)*20);
        if(dbRes == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        return dbRes;
    }


    /**
     * 通过用户id查询订单列表
     * 如果传过来的有storeUuid则是店铺操作，要进行判断联系
     * @param
     * @return
     */
    @Override
    public List<Order> findByConsumerUuid(String consumerUuid, String storeUuid, Integer currentPage) {

        // 如果传进来的有店铺，则是店铺操作，要判断用户和店铺是否存在关系
        if(storeUuid != null && !storeUuid.equals("")){
            if (consumerStoreMapper.selectConsumerStore(consumerUuid, storeUuid) == null){
                throw new SelectException(ServiceResultEnum.DB_CONSUMER_NOT_JOIN.getResult());
            }
        }

        List<Order> dbRes = orderMapper.selectByConsumerUuid(consumerUuid, storeUuid,(currentPage-1)*20);
        if(dbRes == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        return dbRes;
    }

    /**
     * 通过店铺id查询订单列表
     * @param
     * @return
     */
    @Override
    public HashMap<String, Object> findByStoreUuid(String storeUuid, Integer currentPage) {
        List<Order> dbRes = orderMapper.selectByStoreUuid(storeUuid,(currentPage-1)*20);

        if(dbRes == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        for (Order order : dbRes) {
            order.setOrderStatus(numToText(Integer.parseInt(order.getOrderStatus())));
        }

        Integer sum = orderMapper.selectCount(storeUuid);

        HashMap<String, Object> map = new HashMap<>();
        map.put("list", dbRes);
        map.put("sum",sum);

        return map;
    }

    public String numToText(Integer num){
        if(num==0){
            return "预约中";
        }else if(num == 1){
            return "取消";
        }else if(num == 2){
            return "预约成功";
        }else if(num == 3){
            return "操作中";
        }else if(num == 4){
            return "订单完成";
        }else if(num == 5){
            return "订单关闭";
        }
        return "异常状态";
    }


}
