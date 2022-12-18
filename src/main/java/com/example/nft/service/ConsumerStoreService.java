package com.example.nft.service;


import com.example.nft.entity.Consumer;
import com.example.nft.entity.Store;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConsumerStoreService {

    String ConsumerAndStore(String consumerUuid, String storeUuid);

    String removeConsumerStore(String consumerUuid, String storeUuid);

    List<Store> findStoreByConsumerUuid(String consumerUuid);

    List<Consumer> findConsumerByStoreUuid(String storeUuid, Integer currentPage);
}
