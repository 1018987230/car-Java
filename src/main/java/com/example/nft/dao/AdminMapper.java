package com.example.nft.dao;


import com.example.nft.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {

    Boolean insert (Admin admin);

    Admin selectByPhone(String adminPhone);
}
