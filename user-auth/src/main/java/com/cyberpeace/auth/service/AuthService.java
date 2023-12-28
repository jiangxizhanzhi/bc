package com.cyberpeace.auth.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.auth.constant.LicenseEnum;

import java.util.Date;

/**
 * @author :   SN
 * @date :   2023/12/15 16:45
 * @desc :
 **/
public interface AuthService {

    String getSerialNumber();

    LicenseEnum verifySystemCode(String model);

    LicenseEnum registerVerifyInfo(Integer timeRange, String verifyCode, String model);

    void verifyDayAdd();

    JSONObject getVerifyInfo(String model);

    Date getLicenseExpireTime(String model);
}
