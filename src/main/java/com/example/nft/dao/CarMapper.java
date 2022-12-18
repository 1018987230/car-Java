package com.example.nft.dao;

import com.example.nft.entity.Car;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarMapper {

    Boolean insert (Car car);

    Boolean deleteByNumber(String carNumber);

    Integer updateByNumber (Car car);

    List<Car> selectByPhone(String carOwnerPhone);

    Car selectByNumber(String carNumber);

    List<Car> selectByStore( String storeUuid, Integer currentPage);

    Integer selectCount(String storeUuid);
}
