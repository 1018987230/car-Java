package com.example.nft.controller;

import com.example.nft.controller.param.PageParam;
import com.example.nft.entity.Product;
import com.example.nft.service.ProductService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：bbdxgg
 * @date ：Created By 2023/1/5 上午10:24
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {


    @Resource
    private ProductService productService;

    /**
     * 增加产品接口
     * @param product
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Product product){
        String result = productService.add(product);
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 改变非重要信息
     * @param product
     * @return
     */
    @PostMapping("/info/change")
    public Result changeInfo(@RequestBody Product product){

        String result = productService.changeInfo(product);
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 改变物品数量
     * @param product
     * @return
     */
    @PostMapping("/quantity/change")
    public Result changeQuantity(@RequestBody Product product){

        String result = productService.changeQuantity(product.getProductUuid(), product.getProductQuantity());
        return ResultGenerator.genSuccessResult(result);
    }


    /**
     * 改变物品状态　删除or找回
     * @param product
     * @return
     */
    @PostMapping("/status/change")
    public Result changeStatus(@RequestBody Product product){
        String result = productService.changeStatus(product.getProductUuid(), product.getProductStatus());
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 查找批量数据
     * @param pageParam
     * @return
     */
    @PostMapping("/findMany")
    public Result findMany(@RequestBody PageParam pageParam){

        HashMap<String, Object> result = productService.findMany(pageParam.getStoreUuid(), pageParam.getProductName(), pageParam.getCurrentPage());
        return ResultGenerator.genSuccessResult(result);
    }


    /**
     * 根据产品uuid查找
     * @param productUuid
     * @return
     */
    @GetMapping("/findByUuid")
    public Result findByUuid(@RequestParam String productUuid){

        Product result = productService.findByUuid(productUuid);
        return ResultGenerator.genSuccessResult(result);
    }

}
