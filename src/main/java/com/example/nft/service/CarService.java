package com.example.nft.service;

import com.example.nft.entity.Car;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface CarService {

    String add (Car car);

    String removeByNumber(String carOwnerPhone);

    String changeByNumber(Car car);

    Car findByNumber(String storeUuid,String carNumber);

    List<Car> findByPhone(String storeUuid,String carOwnerPhone);

    HashMap<String,Object> findByStore(String storeUuid, Integer currentPage);

}
