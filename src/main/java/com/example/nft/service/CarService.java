package com.example.nft.service;

import com.aliyun.ocr_api20210707.models.RecognizeCarNumberResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.nft.entity.Car;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Service
public interface CarService extends IService<Car> {

    String add (Car car);

    String removeByNumber(String carOwnerPhone);

    String changeByNumber(Car car);

    Car findByNumber(String storeUuid, String carNumber);

    List<Car> findByPhone(String storeUuid, String carOwnerPhone);

    HashMap<String,Object> findByStore(String storeUuid, Integer currentPage);

    RecognizeCarNumberResponse recognize(MultipartFile file) throws Exception;
}
