package com.example.nft.service;


import com.example.nft.entity.Consumer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface ConsumerService {

    String add (String account, String password, String valid);

    String login(String consumerPhone, String password);

    String statusChange(Integer consumerStatus,String consumerPhone);

    String change(Consumer consumer);

    String passwordChange(String consumerPhone, String oldPassword,String newPassword1, String newPassword2);

    List<Consumer> findMany(Integer currentPage);

    List<Consumer> findBanMany(Integer currentPage);

    HashMap<String, Object> findCarAndStore(String consumerPhone);

    int count();

    Consumer findByPhone(String consumerPhone);

    Consumer findByNumber(String carNumber);

    Consumer findByUuid(String uuid);
}
