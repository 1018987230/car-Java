package com.example.nft.dao;

import com.example.nft.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.awt.*;
import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2022/10/23 上午1:59
 * @PackageName:com.example.nft.dao
 * @ClassName: orderMapper
 * @Description: TODO
 * @Version 1.0
 */
@Mapper
public interface OrderMapper {
     Integer insertOrder(Order order);

     Integer updateStatus(String orderUuid,Integer orderStatus);

     Order selectByUuid(String orderUuid);

     /**
      * 通过查询用户的uuid获取订单信息
      * @param orderConsumerUuid
      * @param currentPage
      * @return
      */
     List<Order> selectByConsumerUuid(String orderConsumerUuid, String storeUuid ,Integer currentPage);


     /**
      * 分页查找该店铺下的所有订单
      * @param
      * @return
      */
     List<Order> selectByStoreUuid(String orderStoreUuid, Integer currentPage);



     /**
      * 根据车牌号查询相关订单
      * @param carNumber
      * @param currentPage
      * @return
      */
     List<Order> selectByCarNumber(String carNumber, String storeUuid, Integer currentPage);

     /**
      * 查询该店铺下的订单总数
      * @return
      */
     Integer selectCount(String orderStoreUuid);
}
