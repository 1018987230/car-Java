package com.example.nft.controller;

import com.example.nft.dao.StoreSettingMapper;
import com.example.nft.entity.StoreSetting;
import com.example.nft.service.StoreSettingService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author wangyixiong
 * @Date 2023/2/23 上午2:34
 * @PackageName:com.example.nft.controller
 * @ClassName: StoreSettingController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/storeSetting")
public class StoreSettingController {

    @Resource
    private StoreSettingMapper storeSettingMapper;

    @Resource
    private StoreSettingService storeSettingService;

    @PostMapping("/change")
    public Result add(@RequestBody StoreSetting storeSetting){

        storeSettingService.change(storeSetting);

        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/find/{storeUuid}")
    public Result find(@PathVariable("storeUuid") String storeUuid){

        StoreSetting result = storeSettingService.findByStore(storeUuid);

        return ResultGenerator.genSuccessResult(result);
    }
}
