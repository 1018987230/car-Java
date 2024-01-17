package com.example.nft.controller;


import com.example.nft.controller.param.LoginParam;
import com.example.nft.entity.Admin;
import com.example.nft.service.AdminService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/admin")
public class AdminController extends BaseController{

    @Resource
    private AdminService adminServicecc;

    /**
     * 注册接口
     * @param admin
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody Admin admin){
        System.out.println(admin);
        String result = adminService.register(admin);
        return ResultGenerator.genSuccessResult(result);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginParam loginParam){
        System.out.println(loginParam);
        Object result = adminService.login(loginParam.getAccount(), loginParam.getPassword());
        return ResultGenerator.genSuccessResult(result);
    }
}
