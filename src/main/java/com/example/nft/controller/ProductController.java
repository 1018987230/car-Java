package com.example.nft.controller;

import com.example.nft.entity.Product;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 增加产品接口
     * @param product
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Product product){

        return ResultGenerator.genSuccessResult();
    }
}
