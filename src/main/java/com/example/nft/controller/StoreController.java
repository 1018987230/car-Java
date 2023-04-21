package com.example.nft.controller;


import com.example.nft.commons.AgentThreadLocal;
import com.example.nft.controller.param.StoreParam;
import com.example.nft.entity.Consumer;
import com.example.nft.entity.Store;
import com.example.nft.service.StoreService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/api/store")
@RestController
@Api(tags = "2.店铺模块")
public class StoreController extends BaseController {

    @Resource
    private StoreService storeService;


    /**
     * 添加店铺的接口
     *
     * @param store
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value="添加店铺")
    public Result addStore(@RequestBody Store store) {
        Boolean result = storeService.add(store);
        return ResultGenerator.genSuccessResult(result);
    }


    /**
     * 查询全部店铺接口
     *
     * @return
     */
    @PostMapping("/findAll")
    @ApiOperation(value="查询所有店铺")
    public Result findAll() {
        List<Store> result = storeService.findAll();
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     *
     */
    @PostMapping("/findByName")
    @ApiOperation(value="根据店铺名称查询")
    public Result findByName(@RequestBody StoreParam storeParam) {
        List<Store> result = storeService.findStoreByName(storeParam.getStoreName());
        return ResultGenerator.genSuccessResult(result);
    }


    @PostMapping("/findByUuid/{storeUuid}")
    @ApiOperation(value="根据uuid查询店铺信息")
    public Result findByUuid(@PathVariable("storeUuid") String storeUuid  ){
        Store result = storeService.findStoreByUuid(storeUuid);
        return ResultGenerator.genSuccessResult(result);
    }

}
