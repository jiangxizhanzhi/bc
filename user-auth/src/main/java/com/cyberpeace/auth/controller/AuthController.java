package com.cyberpeace.auth.controller;

import com.cyberpeace.auth.service.AuthService;
import com.cyberpeace.common.model.BaseRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :   SN
 * @date :   2023/12/15 16:44
 * @desc :
 **/
@Api(tags = "license授权")
@RestController
@RequestMapping("/license")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/serialnumber")
    @ApiOperation(value = "获取系统序列号", notes = "获取系统序列号")
    public BaseRsp<String> getSystemSerialNumber(){
        String serialNumber = authService.getSerialNumber();
        return new BaseRsp<>(serialNumber);
    }

    @GetMapping("/verifysystem")
    @ApiOperation(value = "系统是否授权验证", notes = "系统是否授权验证")
    public BaseRsp<Integer> verifySystemCode(){

        Integer verifyResult = authService.verifySystemCode();

        return new BaseRsp<>(verifyResult);
    }

    @GetMapping("/registerverifyinfo")
    @ApiOperation(value = "激活系统",notes = "激活系统")
    public BaseRsp<Integer> registerVerifyInfo(@RequestParam("timeRange")Integer timeRange, @RequestParam("verifyCode") String verifyCode, @RequestParam("number") Integer number){

        Integer registerResult = authService.registerVerifyInfo(timeRange,verifyCode, number);
        return new BaseRsp<>(registerResult);
    }

    @GetMapping("/expireTime")
    @ApiOperation("证书过期时间")
    public BaseRsp licenseExpireTime(){
        return new BaseRsp<>(authService.getLicenseExpireTime());
    }
}
