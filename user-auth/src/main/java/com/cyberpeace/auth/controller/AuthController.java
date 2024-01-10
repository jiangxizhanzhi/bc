package com.cyberpeace.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.auth.constant.LicenseEnum;
import com.cyberpeace.auth.constant.SystemModelEnum;
import com.cyberpeace.auth.service.AuthService;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
    public BaseRsp<JSONObject> verifySystemCode(){

        LicenseEnum verifyResult = authService.verifySystemCode(SystemModelEnum.MODEL_GN.getName());

		// 判断返回值，根据不同的值，抛出不同的异常
		if (verifyResult == LicenseEnum.LICENSE_FAIL) {
			throw new BusinessException(ErrorCode.SYSTEM_NOT_REGISTERED);
		} else if (verifyResult == LicenseEnum.LICENSE_INVALID) {
			throw new BusinessException(ErrorCode.READ_ACTIVATION_FILE_FAILED);
		} else if (verifyResult == LicenseEnum.LICENSE_EXPIRED) {
			throw new BusinessException(ErrorCode.ACTIVATION_EXPIRED);
		} else if (verifyResult == LicenseEnum.LICENSE_NOT_ACTIVATED) {
			throw new BusinessException(ErrorCode.SYSTEM_NOT_ACTIVATED);
		} else if (verifyResult == LicenseEnum.SERIAL_NUMBER_EXCEPTION) {
			throw new BusinessException(ErrorCode.ACTIVATION_CODE_NOT_MATCH);
		}

        return new BaseRsp<>(JSONObject.parseObject(verifyResult.toString()));
    }

    @GetMapping("/registerverifyinfo")
    @ApiOperation(value = "激活系统",notes = "激活系统")
    public BaseRsp<JSONObject> registerVerifyInfo(@RequestParam("timeRange")Integer timeRange, @RequestParam("verifyCode") String verifyCode){

        LicenseEnum registerResult = authService.registerVerifyInfo(timeRange,verifyCode, SystemModelEnum.MODEL_GN.getName());
        return new BaseRsp<>(JSONObject.parseObject(registerResult.toString()));
    }

    @GetMapping("/expireTime")
    @ApiOperation("证书过期时间")
    public BaseRsp licenseExpireTime(){
        return new BaseRsp<>(authService.getLicenseExpireTime(SystemModelEnum.MODEL_GN.getName()));
    }
}
