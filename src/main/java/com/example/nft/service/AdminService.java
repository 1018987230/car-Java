package com.example.nft.service;

import com.example.nft.entity.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    String register(Admin admin);

    Object login(String adminPhone, String adminPassword);
}
