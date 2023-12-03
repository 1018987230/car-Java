package com.example.nft.service.impl;

import com.aliyun.ocr_api20210707.models.RecognizeCarNumberRequest;
import com.aliyun.ocr_api20210707.models.RecognizeCarNumberResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.dao.CarMapper;
import com.example.nft.dao.ConsumerMapper;
import com.example.nft.dao.ConsumerStoreMapper;
import com.example.nft.entity.Car;
import com.example.nft.entity.Consumer;
import com.example.nft.service.CarService;
import com.example.nft.service.ex.DeleteException;
import com.example.nft.service.ex.InsertException;
import com.example.nft.service.ex.SelectException;
import com.example.nft.service.ex.UpdateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aliyun.tea.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {




    @Resource
    private CarMapper carMapper;

    @Resource
    private ConsumerStoreMapper consumerStoreMapper;

    @Resource
    private ConsumerMapper consumerMapper;


    /**
     * 添加车辆
     * @param car
     * @return
     */
    @Override
    public String add(Car car) {
        // 根据carNumber判断车辆是否存在
        System.out.println(carMapper.selectById(car.getCarId()));
        if(carMapper.selectByCarId(car.getCarId()) != null){
            // 执行更改操作
            Integer dbRes = carMapper.updateByCarId(car);
//            throw new InsertException(ServiceResultEnum.DB_EXIST.getResult());
        }else{
            // 插入操作
            if(carMapper.insert(car) == 0){
                throw new InsertException(ServiceResultEnum.DB_INSERT_ERROR.getResult());
            }
        }


        return ServiceResultEnum.SUCCESS.getResult();
    }

    /**
     * 通过车牌号来移除车辆
     * @param carNumber
     * @return
     */
    @Override
    public String removeByNumber(String carNumber) {
        // 根据carNumber判断车辆是否存在
        if(carMapper.selectByNumber(carNumber) == null){
            throw new InsertException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        // 删除操作
        if(!carMapper.deleteByNumber(carNumber)){
            throw new DeleteException(ServiceResultEnum.DB_DELETE_ERROR.getResult());
        }

        return ServiceResultEnum.SUCCESS.getResult();
    }


    /**
     * 通过车牌号查找车辆，更新车辆的详细信息
     * @param car
     * @return
     */
    @Override
    public String changeByNumber(Car car) {
        // 根据carNumber判断车辆是否存在
        if(carMapper.selectByNumber(car.getCarNumber()) == null){
            throw new InsertException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        // 更新操作

        Integer dbRes = carMapper.updateByNumber(car);
        if(dbRes == 0){
            throw new UpdateException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }
        return ServiceResultEnum.SUCCESS.getResult();
    }



    /**
     * 通过车牌号查询车辆
     * @param carNumber
     * @return
     */
    @Transactional
    @Override
    public Car findByNumber(String storeUuid, String carNumber) {
        Car car = carMapper.selectByNumber(carNumber);
        if(car == null){
            throw new SelectException("车辆未加入当前店铺！");
        }
        Consumer consumer = consumerMapper.selectByPhone(car.getCarOwnerPhone());


        // 如果传进来的有店铺，则是店铺操作，要判断用户和店铺是否存在关系
        if(storeUuid != null && !storeUuid.equals("")){
            if (consumerStoreMapper.selectConsumerStore(consumer.getConsumerUuid(), storeUuid) == null){
                throw new SelectException(ServiceResultEnum.DB_CONSUMER_NOT_JOIN.getResult());
            }
        }

        return car;
    }


    /**
     * 查询顾客的全部车辆（ps:毕竟，那种拥有超过20辆车的人也不是没有！！但是为了简单，不分页）
     * @param carOwnerPhone
     * @return
     */
    @Override
    public List<Car> findByPhone(String storeUuid, String carOwnerPhone) {

        // 先查询出结果，再判断是否有关系，没有直接抛异常
        List<Car> cars = carMapper.selectByPhone(carOwnerPhone);

        Consumer consumer = consumerMapper.selectByPhone(carOwnerPhone);

        // 如果传进来的有店铺，则是店铺操作，要判断用户和店铺是否存在关系
        if(storeUuid != null && !storeUuid.equals("")){
            if (consumerStoreMapper.selectConsumerStore(consumer.getConsumerUuid(), storeUuid) == null){
                throw new SelectException(ServiceResultEnum.DB_CONSUMER_NOT_JOIN.getResult());
            }
        }
        return cars;
    }

    /**
     * 查询店铺下的车辆信息
     */
    @Override
    public HashMap<String, Object> findByStore(String storeUuid, Integer currentPage) {
        HashMap<String, Object> map = new HashMap<>();
        List<Car> info = carMapper.selectByStore(storeUuid,(currentPage-1)*20 );

        Integer sum = carMapper.selectCount(storeUuid);

        map.put("info", info);
        map.put("sum", sum);

        return map;
    }

    @Override
    public RecognizeCarNumberResponse recognize(MultipartFile img) throws Exception {

        com.aliyun.ocr_api20210707.Client client = createClient(System.getenv("LTAI5tF9AbG4ojYnT1KYiTRb"), System.getenv("IlIdi1PgKP0VxiX5HoN033lf5V389r"));
        com.aliyun.ocr_api20210707.models.RecognizeCarNumberRequest recognizeCarNumberRequest = new com.aliyun.ocr_api20210707.models.RecognizeCarNumberRequest();
        final RecognizeCarNumberRequest recognizeCarNumberRequest1 = recognizeCarNumberRequest.setBody(img.getInputStream());
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            RecognizeCarNumberResponse recognizeCarNumberResponse = client.recognizeCarNumberWithOptions(recognizeCarNumberRequest, runtime);
            System.out.println(recognizeCarNumberRequest.getBody());
            return recognizeCarNumberResponse;
        } catch (TeaException error) {
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return null;
    }

    public static com.aliyun.ocr_api20210707.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId("LTAI5tF9AbG4ojYnT1KYiTRb")
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret("IlIdi1PgKP0VxiX5HoN033lf5V389r");
        // Endpoint 请参考 https://api.aliyun.com/product/ocr-api
        config.endpoint = "ocr-api.cn-hangzhou.aliyuncs.com";
        return new com.aliyun.ocr_api20210707.Client(config);
    }
}
