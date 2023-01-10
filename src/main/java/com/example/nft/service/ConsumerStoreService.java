package com.example.nft.service;


import com.example.nft.entity.Consumer;
import com.example.nft.entity.Store;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface ConsumerStoreService {

    String ConsumerAndStore(String consumerUuid, String storeUuid);

    String removeConsumerStore(String consumerUuid, String storeUuid);

    HashMap<String, Object> findStoreByConsumerUuid(String consumerUuid);

    HashMap<String, Object> findConsumerByStoreUuid(String storeUuid, Integer currentPage);
}
