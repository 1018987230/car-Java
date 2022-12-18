package com.example.nft.service;

import com.example.nft.entity.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2022/10/23 上午2:07
 * @PackageName:com.example.nft.service
 * @ClassName: Order
 * @Description: TODO
 * @Version 1.0
 */
@Service
public interface OrderService {
    String addOrder(String orderConsumerId, String orderStoreUuid, String orderCarNumber, String orderService, String orderNote, String orderPreordainTime);

    // 更改订单状态
    String changeStatus(String orderUuid,Integer orderStatus);

    /**
     * 通过订单uuid获取订单信息，强制装为list<order>，方便展示
     * @param orderUuid
     * @return
     */
    List<Order> findByUuid(String orderUuid);

    /**
     * 通过用户uuid获取订单信息
     * @param orderConsumerUuid
     * @param currentPage
     * @return
     */
    List<Order> findByConsumerUuid(String orderConsumerUuid,String storeUuid, Integer currentPage);

    /**
     * 通过用户手机号获取用户的uuid，再对用户的uuid进行查询订单
     * @param orderConsumerPhone
     * @param currentPage
     * @return
     */
    List<Order> findByConsumerPhone(String orderConsumerPhone, String storeUuid,Integer currentPage);

    /**
     *  根据店铺id查询订单
     * @param orderStoreId
     * @param currentPage
     * @return
     */
    HashMap<String, Object> findByStoreUuid(String orderStoreId, Integer currentPage);


    /**
     * 通过
     * @param carNumber
     * @param currentPage
     * @return
     */
    List<Order> findByCarNumber(String carNumber,String storeUuid, Integer currentPage);

}
