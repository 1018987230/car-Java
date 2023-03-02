package com.example.nft.service;

import com.example.nft.entity.Operator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2022/11/27 下午10:29
 * @PackageName:com.example.nft.service
 * @ClassName: OperatorService
 * @Description: TODO
 * @Version 1.0
 */
@Service
public interface OperatorService {

    /**
     * 注册
     * @param operator
     * @return
     */
    String add(Operator operator);

    /**
     * 登录
     * @param operatorBelong
     * @param operatorPhone
     * @return
     */
    HashMap<String,Object> login(String operatorBelong, String operatorPhone, String operatorPassword);

    /**
     * 获取操作员列表
     * @param storeUuid
     * @return
     */
    List<Operator> findMany(String storeUuid);


    /**
     * 更新操作员状态
     * @param operatorBelong
     * @param operatorPhone
     * @param operatorStatus
     * @return
     */
    String changeStatus(String operatorBelong, String operatorPhone, Integer operatorStatus);


    /**
     * 更改操作员密码
     * @param operatorBelong
     * @param operatorPhone
     * @param newPassword
     * @return
     */
    String changePassword(String operatorBelong, String operatorPhone, String oldPassword, String newPassword);
}
