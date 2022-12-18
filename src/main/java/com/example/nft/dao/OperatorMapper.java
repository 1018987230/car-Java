package com.example.nft.dao;

import com.example.nft.entity.Operator;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author wangyixiong
 * @Date 2022/11/27 下午10:09
 * @PackageName:com.example.nft.dao
 * @ClassName: OperatorMapper
 * @Description: TODO
 * @Version 1.0
 */
@Mapper
public interface OperatorMapper {

    /**
     * 插入店铺操作员
     * @param operator
     * @return
     */
    Integer insert(Operator operator);

    /**
     * 根据手机号，属于的店铺uuid，判断店铺操作员是否存在
     * @param operatorBelong
     * @param operatorPhone
     * @return
     */
    Operator selectByPhoneAndUuid(String operatorBelong, String operatorPhone);

    /**
     * 根据手机号，属于的店铺uuid，更新店铺操作员状态
     * @param operatorBelong
     * @param operatorPhone
     * @param operatorStatus
     * @return
     */
    Integer updateStatusByPhoneAndUuid(String operatorBelong, String operatorPhone, Integer operatorStatus);


    /**
     * 根据手机号，属于的店铺uuid，更新店铺操作员密码
     * @param operatorBelong
     * @param operatorPhone
     * @param operatorPassword
     * @return
     */
    Integer updatePasswordByPhoneAndUuid(String operatorBelong, String operatorPhone, String operatorPassword);

}
