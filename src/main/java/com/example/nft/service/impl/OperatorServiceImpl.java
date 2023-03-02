package com.example.nft.service.impl;

import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.dao.OperatorMapper;
import com.example.nft.dao.StoreMapper;
import com.example.nft.entity.Operator;
import com.example.nft.entity.Store;
import com.example.nft.entity.StoreSetting;
import com.example.nft.service.OperatorService;
import com.example.nft.service.StoreSettingService;
import com.example.nft.service.ex.InsertException;
import com.example.nft.service.ex.SelectException;
import com.example.nft.service.ex.UpdateException;
import com.example.nft.utils.JwtUtil;
import com.example.nft.utils.MD5Util;
import com.example.nft.utils.RedisUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author wangyixiong
 * @Date 2022/11/27 下午10:35
 * @PackageName:com.example.nft.service.impl
 * @ClassName: OperatorServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class OperatorServiceImpl implements OperatorService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private OperatorMapper operatorMapper;

    @Resource
    private StoreMapper storeMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private StoreSettingService storeSettingService;

    @Override
    public String add(Operator operator) {
        // 根据操作员所属店铺和手机号查询是否存在此人
        if(operatorMapper.selectByPhoneAndUuid(operator.getOperatorBelong(),operator.getOperatorPhone(),0) != null){
            throw new InsertException(ServiceResultEnum.DB_EXIST.getResult());
        }

        // 密码加密
        // 生成盐值，拼接上password
        String salt = MD5Util.generateSalt();
        operator.setOperatorSalt(salt);
        String password = salt + operator.getOperatorPassword();

        // 对密码加密
        password = MD5Util.passwordMD5(password);

        operator.setOperatorPassword(password);

        // 插入操作
        if(operatorMapper.insert(operator) == 0){
            throw  new InsertException(ServiceResultEnum.DB_INSERT_LOG_ERROR.getResult());
        }
        logger.info(operator.getOperatorBelong() +"： 新增操作员： "+ operator.getOperatorPhone() );
        return ServiceResultEnum.SUCCESS.getResult();
    }

    @Override
    public HashMap<String, Object> login(String operatorBelong, String operatorPhone, String operatorPassword) {
        // 根据操作员所属店铺和手机号查询是否存在此人
        Operator dbRes = operatorMapper.selectByPhoneAndUuid(operatorBelong, operatorPhone,0);
        Store store = storeMapper.selectByUuid(operatorBelong);

        if(dbRes == null){
            throw new InsertException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }

        // 密码加密
        // 获取盐值，拼接上password
        String password = dbRes.getOperatorSalt() + operatorPassword;

        // 对密码加密
        password = MD5Util.passwordMD5(password);

        //对比数据库密码
        if(!password.equals(dbRes.getOperatorPassword())){
            throw new SelectException(ServiceResultEnum.DB_PASSWORD_ERROR.getResult());
        }
        //生成token验证
        String token =  JwtUtil.genToken(operatorPhone);
        // 生成的token保存在redis中，key为店铺uuid拼接上用户手机号
        redisUtil.set(operatorPhone,token,60*60*24);
        // 获取配置
        StoreSetting setting =  storeSettingService.findByStore(operatorBelong);


        HashMap<String, Object> map = new HashMap<>();
        map.put("token", token);
        dbRes.setOperatorPassword("");
        map.put("operator", dbRes.getOperatorPhone());
        map.put("store", store.getStoreUuid());
        map.put("setting", setting);
        return map;
    }

    @Override
    public List<Operator> findMany(String storeUuid) {
        return operatorMapper.selectMany(storeUuid);
    }


    @Override
    public String changeStatus(String operatorBelong, String operatorPhone, Integer operatorStatus) {
        // 根据操作员所属店铺和手机号查询是否存在此人

        if(operatorMapper.selectByPhoneAndUuid(operatorBelong, operatorPhone,0)==null && operatorMapper.selectByPhoneAndUuid(operatorBelong, operatorPhone,1) == null){
            throw new InsertException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        System.out.println(operatorStatus);
        if(operatorMapper.updateStatusByPhoneAndUuid(operatorBelong,operatorPhone,operatorStatus) == 0){
            throw new UpdateException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }

        return ServiceResultEnum.SUCCESS.getResult();
    }

    @Override
    public String changePassword(String operatorBelong, String operatorPhone, String oldPassword , String newPassword) {

        // 根据操作员所属店铺和手机号查询是否存在此人
        Operator dbRes = operatorMapper.selectByPhoneAndUuid(operatorBelong, operatorPhone,0);
        if(dbRes == null){
            throw new InsertException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }

        // 密码加密
        // 获取盐值，拼接上password
        String password = dbRes.getOperatorSalt() + oldPassword;

        // 对密码加密
        password = MD5Util.passwordMD5(password);

        //对比数据库密码
        if(!password.equals(dbRes.getOperatorPassword())){
            throw new SelectException(ServiceResultEnum.DB_PASSWORD_ERROR.getResult());
        }

        //对新密码获取盐值，加密
        String operatorPassword = dbRes.getOperatorSalt() + newPassword;
        operatorPassword = MD5Util.passwordMD5(operatorPassword);

        //更新密码 0b3dd16df5fab240c2516e19078ea737
        if(operatorMapper.updatePasswordByPhoneAndUuid(operatorBelong,operatorPhone,operatorPassword) == 0){
            throw new UpdateException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }
        System.out.println(operatorPassword);
        return ServiceResultEnum.SUCCESS.getResult();
    }
}
