package com.example.nft.controller.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginParam implements Serializable {

    private String account;

    private String password;
}
