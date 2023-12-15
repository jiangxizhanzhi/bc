package com.cyberpeace.auth.service;

/**
 * @author :   SN
 * @date :   2023/12/15 16:47
 * @desc :
 **/
public interface VerifyLicenseService {

    Integer verifyCode(String serialNumber, Integer range, String verifyCode, Integer number);
}
