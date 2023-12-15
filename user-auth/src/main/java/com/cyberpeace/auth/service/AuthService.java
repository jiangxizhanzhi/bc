package com.cyberpeace.auth.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * @author :   SN
 * @date :   2023/12/15 16:45
 * @desc :
 **/
public interface AuthService {

    String getSerialNumber();

    Integer verifySystemCode();

    Integer registerVerifyInfo(Integer timeRange, String verifyCode, Integer number);

    void verifyDayAdd();

    JSONObject getVerifyInfo();

    Date getLicenseExpireTime();
}
