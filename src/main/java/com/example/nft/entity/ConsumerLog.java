package com.example.nft.entity;

import lombok.Data;

@Data
public class ConsumerLog {
    private Integer consumer_log_id;
    private String consumer_log_phone;
    private String consumer_log_type;
    private String create_time;
}
