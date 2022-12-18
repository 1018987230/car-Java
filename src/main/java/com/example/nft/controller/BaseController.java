package com.example.nft.controller;

import com.example.nft.service.ex.*;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(ServiceException.class)
    public Result handleException(Throwable e){

        // 未登录
        if(e instanceof LoginException){
            return ResultGenerator.genFailResult(1001,"请先登录！");
        }else if(e instanceof DuplicateException){
            return ResultGenerator.genFailResult(2000, e.getMessage());
        // 数据库插入错误
        }else if(e instanceof InsertException){
            return ResultGenerator.genFailResult(2001, e.getMessage());
        // 数据库删除错误
        }else if(e instanceof DeleteException){
            return ResultGenerator.genFailResult(3001, e.getMessage());
        // 数据库更新错误
        }else if(e instanceof UpdateException){
            return ResultGenerator.genFailResult(4001, e.getMessage());
        // 数据库查询错误
        }else if(e instanceof SelectException){
            return ResultGenerator.genFailResult(5001,e.getMessage());
        // 余额异常
        }else if(e instanceof  BalanceException){
            return ResultGenerator.genFailResult(4002, e.getMessage());
        }else if(e instanceof  ParamException){
            return ResultGenerator.genFailResult(5002, e.getMessage());
        }

        return ResultGenerator.genFailResult(400,"");
    }
}
