package com.example.nft.entity;


import lombok.Data;

@Data
public class Admin {

    private Integer adminId;
    private String adminPhone;
    private String adminPassword;
    private String adminName;
    private Integer isDelete;


}
