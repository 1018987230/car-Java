package com.example.nft.service.impl;

import com.example.nft.controller.param.ChargeOrderParam;
import com.example.nft.entity.ChargeOrder;
import com.example.nft.dao.ChargeOrderMapper;
import com.example.nft.service.ChargeOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.nft.service.ex.InsertException;
import com.example.nft.utils.RandomUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxx
 * @since 2024-03-20
 */
@Service
public class ChargeOrderServiceImpl extends ServiceImpl<ChargeOrderMapper, ChargeOrder> implements ChargeOrderService {

    @Autowired
    private ChargeOrderMapper chargeOrderMapper;

    @Override
    public ChargeOrder createOrder(ChargeOrderParam chargeOrderParam) {

        ChargeOrder chargeOrder = new ChargeOrder();
        String id = RandomUtil.generateRandom(16);
        BeanUtils.copyProperties(chargeOrderParam,chargeOrder);
        chargeOrder.setId(id);
        chargeOrder.setStatus(0);

        int i = chargeOrderMapper.insert(chargeOrder);
        if(i != 1){
            throw new InsertException("添加订单失败");
        }
        return chargeOrder;
    }
}
