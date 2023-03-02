package com.example.nft.service;

import com.example.nft.entity.OpenidPhone;
import org.springframework.stereotype.Service;

/**
 * @Author wangyixiong
 * @Date 2023/2/26 上午12:36
 * @PackageName:com.example.nft.service
 * @ClassName: OpenidPhoneService
 * @Description: TODO
 * @Version 1.0
 */
@Service
public interface OpenidPhoneService {
    String add(OpenidPhone openidPhone);

    String findByOpenId(String openid);
}
