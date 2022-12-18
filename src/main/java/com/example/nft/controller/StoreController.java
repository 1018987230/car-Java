package com.example.nft.controller;


import com.example.nft.controller.param.StoreParam;
import com.example.nft.entity.Consumer;
import com.example.nft.entity.Store;
import com.example.nft.service.StoreService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/api/store")
@RestController
public class StoreController extends BaseController{

    @Resource
    private StoreService storeService;


    /**
     * 添加店铺的接口
     * @param store
     * @return
     */
    @PostMapping("/add")
    public Result addStore(@RequestBody Store store){
        Boolean result = storeService.add(store);

        return ResultGenerator.genSuccessResult(result);
    }


    /**
     * 查询全部店铺接口
     * @return
     */
    @PostMapping("/findAll")
    public Result findAll(){
        List<Store> result = storeService.findAll();
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     *
     */
    @PostMapping("/findByName")
    public Result findByName(@RequestBody StoreParam storeParam){
        List<Store> result = storeService.findStoreByName(storeParam.getStoreName());
        return ResultGenerator.genSuccessResult(result);
    }

}
