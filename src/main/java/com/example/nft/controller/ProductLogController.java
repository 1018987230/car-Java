package com.example.nft.controller;

import com.example.nft.controller.param.PageParam;
import com.example.nft.entity.Product;
import com.example.nft.service.ProductLogService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author ：bbdxgg
 * @date ：Created By 2023/1/5 上午10:24
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("/api/productLog")
public class ProductLogController extends BaseController {

    @Resource
    private ProductLogService productLogService;

    @PostMapping("/findMany")
    public Result findMany(@RequestBody PageParam pageParam){
        HashMap<String, Object> result = productLogService.findMany(pageParam.getStoreUuid(), pageParam.getProductUuid(), pageParam.getProductName(), pageParam.getCurrentPage());

        return ResultGenerator.genSuccessResult(result);
    }

}
