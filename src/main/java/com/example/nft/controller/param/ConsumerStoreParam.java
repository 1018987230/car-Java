package com.example.nft.controller.param;


import lombok.Data;

import java.io.Serializable;

@Data
public class ConsumerStoreParam implements Serializable {

    private String consumerUuid;

    private String storeUuid;

    private Integer currentPage;
}
