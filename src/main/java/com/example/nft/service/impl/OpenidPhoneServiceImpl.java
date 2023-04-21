package com.example.nft.service.impl;

import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.dao.OpenidPhoneMapper;
import com.example.nft.entity.OpenidPhone;
import com.example.nft.service.OpenidPhoneService;
import com.example.nft.service.ex.InsertException;
import com.example.nft.service.ex.SelectException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author wangyixiong
 * @Date 2023/2/26 上午12:37
 * @PackageName:com.example.nft.service.impl
 * @ClassName: OpenidPhoneServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class OpenidPhoneServiceImpl implements OpenidPhoneService {


    @Resource
    private OpenidPhoneMapper openidPhoneMapper;

    @Override
    public String add(OpenidPhone openidPhone) {
        if(openidPhoneMapper.insert(openidPhone) == null){
            throw new InsertException("插入失败");
        }

        return ServiceResultEnum.SUCCESS.getResult();
    }

    @Override
    public OpenidPhone findByOpenId(String openid) {
        if(openidPhoneMapper.selectByOpenid(openid) == null){
            return null;
        }
        return openidPhoneMapper.selectByOpenid(openid);
    }
}
