package com.example.nft.service.impl;

import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.dao.*;
import com.example.nft.entity.Car;
import com.example.nft.entity.Consumer;
import com.example.nft.entity.Store;
import com.example.nft.service.ConsumerService;
import com.example.nft.service.ex.*;
import com.example.nft.utils.MD5Util;
import com.example.nft.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ConsumerMapper consumerMapper;

    @Resource
    private CarMapper carMapper;

    @Resource
    private ConsumerStoreMapper consumerStoreMapper;

    @Resource
    private ConsumerBalanceMapper consumerBalanceMapper;

    @Resource
    private RedisUtil redisUtil;
    /**
     *  增加用户
     * @param
     * @return
     */
    @Override
    public String add(String account, String password, String valid) {


        // 验证码校验，从redis中通过手机号获取
        if(!valid.equals(redisUtil.get(account))){
            throw new InsertException(ServiceResultEnum.VALID_ERROR.getResult());
        }

        Consumer consumer = new Consumer();

        // 生成用户uuid
        String consumerUuid ="C" + (new Date().getTime() + (int)((Math.random()*9+1)*100000) + "").substring(2);
        consumer.setConsumerPhone(account);
        consumer.setConsumerPassword(password);
        consumer.setConsumerUuid(consumerUuid);
        consumer.setConsumerName("顾客"+consumerUuid);

        //根据传过来的用户手机号查找数据库，判断用户是否已经存在
        if(consumerMapper.selectByPhone(consumer.getConsumerPhone()) != null){
            throw new DuplicateException(ServiceResultEnum.DB_EXIST.getResult());
        }

        // 生成盐值，拼接上password
        String salt = MD5Util.generateSalt();
        consumer.setConsumerSalt(salt);
        password = salt + password;

        // 对密码加密
        password = MD5Util.passwordMD5(password);

        consumer.setConsumerPassword(password);

        // 用户表插入
        if(!consumerMapper.insert(consumer)){
            throw new InsertException(ServiceResultEnum.DB_INSERT_ERROR.getResult());
        }
        logger.info("用户加入成功，手机号: "+ consumer.getConsumerPhone());
        return ServiceResultEnum.SUCCESS.getResult();
    }


    /**
     * 用户登录
     * @param consumerPhone
     * @param password
     * @return
     */
    @Override
    public String login(String consumerPhone, String password) {
        // 1.查询出consumerPhone是否存在。若在保留信息
        Consumer dbRes = consumerMapper.selectByPhone(consumerPhone);
        if(dbRes == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }

        // 2.取出对应的盐值，拼接到password上
        String salt = dbRes.getConsumerSalt();
        password = salt + password;

        // 3.对password进行md5加密
        password = MD5Util.passwordMD5(password);

        // 4.加密后的password与数据库中的进行比对
        if(!password.equals(dbRes.getConsumerPassword())){
            throw new SelectException(ServiceResultEnum.DB_PASSWORD_ERROR.getResult());
        }


        return ServiceResultEnum.SUCCESS.getResult();
    }

    /**
     * 修改用户以及余额账号状态
     * @param consumerPhone
     * @return
     */
    @Override
    public String statusChange(Integer consumerStatus ,String consumerPhone) {

        // 操作判断
        // 判断是否存在此用户(查询状态为 0 和 1的情况下)
        if(consumerMapper.selectByPhone(consumerPhone) == null & consumerMapper.selectByPhoneBan(consumerPhone) == null){
            throw new DeleteException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        // 状态修改
        if(!consumerMapper.statusByPhone(consumerStatus ,consumerPhone)){
            throw new DeleteException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
        }
//        if(!consumerBalanceMapper.statusBalance(consumerStatus,consumerPhone)){
//            throw new DeleteException(ServiceResultEnum.DB_UPDATE_ERROR.getResult());
//        }
        return ServiceResultEnum.SUCCESS.getResult();
    }

    /**
     * 修改用户信息
     * @param consumer
     * @return
     */
    @Override
    public String change(Consumer consumer) {

        System.out.println(consumer.getConsumerName());
        // 判断修改信息中的用户名是否为null，为null 抛异常
        if(consumer.getConsumerName() == null || consumer.getConsumerName().equals("")){
            throw new ParamException(ServiceResultEnum.PARAM_NAME_NOT_EXIST.getResult());
        }

        // 根据手机号判断是否存在此用户，不存在抛出异常
        Consumer dbRes = consumerMapper.selectByPhone(consumer.getConsumerPhone());
        if(dbRes == null){
            return ServiceResultEnum.DB_NOT_EXIST.getResult();
        }

        // 对consumer进行更新操作
        if(!consumerMapper.updateByPhone(consumer)){
            return ServiceResultEnum.DB_UPDATE_ERROR.getResult();
        }
        return ServiceResultEnum.SUCCESS.getResult();
    }


    /**
     * 用户密码修改
     * @param consumerPhone
     * @param
     * @return
     */
    @Override
    public String passwordChange(String consumerPhone, String oldPassword,String newPassword1, String newPassword2) {

        // 根据手机号判断是否存在此用户，不存在抛出异常
        Consumer dbRes = consumerMapper.selectByPhone(consumerPhone);
        if(dbRes == null){
            return ServiceResultEnum.DB_NOT_EXIST.getResult();
        }

        // 获取保存在用户数据中的盐值
        String salt = dbRes.getConsumerSalt();

        // 旧密码+盐值==>MD5加密
        oldPassword = MD5Util.passwordMD5(salt + oldPassword);

        // 校验旧密码是否正确
        if(!dbRes.getConsumerPassword().equals(oldPassword)){
            throw new UpdateException(ServiceResultEnum.DB_PASSWORD_ERROR.getResult());
        }
        // 校验两次输入的新密码是否一致
        if(!newPassword1.equals(newPassword2)){
            throw new ParamException(ServiceResultEnum.PARAM_TWO_PASSWORD_NOT_SAME.getResult());
        }

        // 对传入的新密码拼接上盐值
        String password = salt + newPassword1;
        // 对拼接后的密码进行加密
        password = MD5Util.passwordMD5(password);

        // 对consumer进行更新操作
        if(!consumerMapper.updatePasswordByPhone(consumerPhone,password)){
            return ServiceResultEnum.DB_UPDATE_ERROR.getResult();
        }
        return ServiceResultEnum.SUCCESS.getResult();
    }




    /**
     * 分页查询 查询20个
     * @param currentPage
     * @return
     */
    @Override
    public List<Consumer> findMany(Integer currentPage) {
        return consumerMapper.selectMany((currentPage-1)*20);
    }


    /**
     *
     * 分页查询禁用顾客 查询20个
     * @param currentPage
     * @return
     */
    @Override
    public List<Consumer> findBanMany(Integer currentPage) {
        return consumerMapper.selectBanMany((currentPage-1)*20);
    }

    @Override
    public HashMap<String, Object> findCarAndStore(String consumerPhone) {
        HashMap<String, Object> map = new HashMap<>();
        // 判断用户是否存在
        Consumer consumer = consumerMapper.selectByPhone(consumerPhone);
        if(consumer == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }

        // 对应的车辆
        List<Car> cars = carMapper.selectByPhone(consumerPhone);

        // 对应的店铺
        List<Store> stores = consumerStoreMapper.selectStoreByConsumerUuid(consumer.getConsumerUuid());


        // 返回前端的车辆列表
        ArrayList<Object> carList = new ArrayList<>();
        // 返回前端的店铺列表
        ArrayList<Object> storeList = new ArrayList<>();


        for(Car car : cars){
            carList.add(car.getCarNumber());
        }

        HashMap<Object, Object> nameUuid = new HashMap<>();
        for(Store store : stores){
            nameUuid.put(store.getStoreName(), store.getStoreUuid());
//            storeList.add(store.getStoreName());
        }
        System.out.println("carList: " + carList);

        storeList.add(nameUuid);

        map.put("cars", carList);
        map.put("stores", nameUuid);

        return map;
    }


    /**
     * 查询顾客总条数
     * @return
     */
    @Override
    public int count() {
        return consumerMapper.count();
    }

    /**
     * 单个查询用户
     * @param consumerPhone
     * @return
     */
    @Override
    public Consumer findByPhone(String consumerPhone) {
        if(consumerMapper.selectByPhone(consumerPhone) ==null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        return consumerMapper.selectByPhone(consumerPhone);
    }

    /**
     *根据车牌号
     * @param
     * @return
     */
    @Override
    public Consumer findByNumber(String carNumber) {
        // 根据车牌号是否存在
        Car db_car =  carMapper.selectByNumber(carNumber);
        if(db_car == null){
            throw new SelectException(ServiceResultEnum.DB_CAR_NOT_EXIST.getResult());
        }
        // 查询人员是否存在
        Consumer db_consumer = consumerMapper.selectByPhone(db_car.getCarOwnerPhone());
        if (db_consumer == null){
            throw new SelectException(ServiceResultEnum.DB_NOT_EXIST.getResult());
        }
        return db_consumer;
    }
}
