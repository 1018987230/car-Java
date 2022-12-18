
package com.example.nft.commons;

public enum ServiceResultEnum {
    ERROR("error"),

    SUCCESS("success"),

    VALID_ERROR("验证码错误！"),

    DB_PASSWORD_ERROR("登录密码错误！"),

    DB_EXIST("查询数据库已经存在！"),

    DB_INSERT_ERROR("数据库插入错误！"),

    DB_DELETE_ERROR("数据库删除错误！"),

    DB_UPDATE_ERROR("数据库更新错误！"),

    DB_NOT_EXIST("查询数据库不存在！"),

    DB_PHONE_NOT_EXIST("查询的手机号不存在"),

    DB_CONSUMER_NOT_JOIN("用户未加入店铺"),

    DB_CAR_NOT_EXIST("车辆不存在！"),

    BALANCE_NOT_ENOUGH("余额不足，请及时充值！"),

    DB_INSERT_LOG_ERROR("余额操作插入记录失败！"),


    LOGIN_PASSWORD_ERROR("Password is wrong!"),

    LOGIN_VERIFY_CODE_ERROR("Code is wrong！"),

    DB_ERROR("database error"),


    SAME_EMAIL_EXIST("邮箱已存在！"),

    SAME_EMAIL_NOT_EXIST("邮箱不存在！"),

    EMAIL_DELETE("请勿重复删除邮箱！"),

    // =============订单相关===========
    ORDER_INSERT_ERROR("订单插入失败！"),

    ORDER_PROHIBIT_MODIFIED("订单已完成，禁止修改！"),

    // ============账户异常===============
    //重复插入账户
    BALANCE_EXIST("当前店铺已经存在存在账户！"),

    // 插入账户失败
    BALANCE_INSERT_ERROR("余额账户创建失败！"),

    // =============参数异常================
    PARAM_NAME_NOT_EXIST("昵称不能为空！"),


    PARAM_TWO_PASSWORD_NOT_SAME("两次输入的密码不一致！");




    private String result;

    ServiceResultEnum(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
