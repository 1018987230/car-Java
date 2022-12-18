package com.example.nft.controller.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailParam implements Serializable {
    private String toEmail;
}
