package com.example.nft.service.impl;

import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.dao.AdminMapper;
import com.example.nft.entity.Admin;
import com.example.nft.service.AdminService;
import com.example.nft.service.ex.DuplicateException;
import com.example.nft.service.ex.InsertException;
import com.example.nft.service.ex.SelectException;
import com.example.nft.utils.JwtUtil;
import com.example.nft.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private RedisUtil redisUtil;

    /**
     *  管理员注册
     * @param admin
     * @return
     */
    @Override
    public String register(Admin admin) {
        // 判断是否已经存在
        if(adminMapper.selectByPhone(admin.getAdminPhone()) != null){
            throw new DuplicateException(ServiceResultEnum.DB_EXIST.getResult());
        }
        // 插入操作
        if (!adminMapper.insert(admin)){
            throw new InsertException(ServiceResultEnum.DB_INSERT_ERROR.getResult());
        }
        return ServiceResultEnum.SUCCESS.getResult();
    }

    /**
     * 管理员登录
     * @param adminPhone
     * @param adminPassword
     * @return
     */
    @Override
    public Object login(String adminPhone, String adminPassword) {
        // 判断是否已经存在
        Admin dbRes = adminMapper.selectByPhone(adminPhone);
        if(dbRes == null){
            throw new DuplicateException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        // 密码校验
        if(!adminPassword.equals(dbRes.getAdminPassword())){
            throw new SelectException(ServiceResultEnum.DB_PASSWORD_ERROR.getResult());
        }
        // 生成token
        Object token = JwtUtil.genToken(adminPhone);
        redisUtil.set(adminPhone,token,60*60*24);
        return token;
    }
}
