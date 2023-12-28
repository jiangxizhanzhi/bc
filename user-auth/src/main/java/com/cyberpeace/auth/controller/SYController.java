package com.cyberpeace.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.auth.constant.LicenseEnum;
import com.cyberpeace.auth.constant.SystemModelEnum;
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
 * @date :   2023/12/28 14:43
 * @desc :
 **/
@Api(tags = "水印权限管理")
@RestController
@RequestMapping("/sy")
public class SYController {

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
    public BaseRsp<JSONObject> verifySystemCode(){

        LicenseEnum verifyResult = authService.verifySystemCode(SystemModelEnum.MODEL_SY.getName());

        return new BaseRsp<>(JSONObject.parseObject(verifyResult.toString()));
    }

    @GetMapping("/registerverifyinfo")
    @ApiOperation(value = "激活系统",notes = "激活系统")
    public BaseRsp<JSONObject> registerVerifyInfo(@RequestParam("timeRange")Integer timeRange, @RequestParam("verifyCode") String verifyCode){

        LicenseEnum registerResult = authService.registerVerifyInfo(timeRange,verifyCode, SystemModelEnum.MODEL_SY.getName());
        return new BaseRsp<>(JSONObject.parseObject(registerResult.toString()));
    }

    @GetMapping("/expireTime")
    @ApiOperation("证书过期时间")
    public BaseRsp licenseExpireTime(){
        return new BaseRsp<>(authService.getLicenseExpireTime(SystemModelEnum.MODEL_SY.getName()));
    }
}
