package com.cyberpeace.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.auth.constant.AuthConstant;
import com.cyberpeace.auth.constant.LicenseEnum;
import com.cyberpeace.auth.service.AuthService;
import com.cyberpeace.auth.service.VerifyLicenseService;
import com.cyberpeace.auth.util.AESUtil;
import com.cyberpeace.auth.util.CommonUtil;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.EnumUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

/**
 * @author :   SN
 * @date :   2023/12/15 16:45
 * @desc :
 **/
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private static final String VERIFY_FILE_PATH = ConfigUtil.getContextProperty("license.verify.file");

    @Autowired
    private VerifyLicenseService verifyLicenseService;

    @Override
    public String getSerialNumber() {
        String uuid = CommonUtil.getSystemUuid();
        if (null == uuid) {
            throw new BusinessException("获取系统序列号失败");
        }

        //需求只要20位，uuid有32位和4个-，
        //去掉-
        uuid = uuid.replace("-", "");
        //取20位，并用-分成5段
        uuid = uuid.substring(1, 5) + "-" + uuid.substring(7, 11) + "-" + uuid.substring(13, 17) + "-" + uuid.substring(19, 23) + "-" + uuid.substring(27, 31);
        return uuid;
    }

    @Override
    public LicenseEnum verifySystemCode() {
        JSONObject verifyJsonObject = getVerifyInfo();
        if (verifyJsonObject.containsKey("errorCode")) {
            log.error("errorCode: {}", verifyJsonObject.containsKey("errorCode"));
            return LicenseEnum.LICENSE_NOT_ACTIVATED;
        }
        Long verifyTime = verifyJsonObject.getLong("time");
        Integer range = verifyJsonObject.getInteger("range");
        Integer day = verifyJsonObject.containsKey("day") ? verifyJsonObject.getInteger("day") : 1;
        Integer number = verifyJsonObject.containsKey("num") ? verifyJsonObject.getInteger("num") : 5;
        if (CommonUtil.verifyTimeValid(verifyTime, range) && day <= range * 30) {
            //在注册有效期内
            String code = verifyJsonObject.getString("code");
            String serialNumber = getSerialNumber();
            Integer integer = verifyLicenseService.verifyCode(serialNumber, range, code, number);
            return EnumUtils.getEnumByCode(integer, LicenseEnum.class);
        } else {
            return LicenseEnum.LICENSE_EXPIRED;
        }
    }

    /**
     * 激活流程
     *
     * @param timeRange
     * @param verifyCode
     * @param licenseNum 新的激活码对应的可注册数量
     * @return
     */
    @Override
    public LicenseEnum registerVerifyInfo(Integer timeRange, String verifyCode, Integer licenseNum) {
        String serialNumber = getSerialNumber();
        Integer verifyResult = verifyLicenseService.verifyCode(serialNumber, timeRange, verifyCode, licenseNum);
        if (LicenseEnum.SUCCESS.getCode().equals(verifyResult)) {

            JSONObject verifyJsonObject = new JSONObject();
            verifyJsonObject.put("time", System.currentTimeMillis());
            verifyJsonObject.put("range", timeRange);
            verifyJsonObject.put("code", verifyCode);
            verifyJsonObject.put("num", licenseNum);
            verifyJsonObject.put("day", 1);

            try {
                String enVerifyInfo = AESUtil.encrypt(verifyJsonObject.toString());
                File file = new File(VERIFY_FILE_PATH);
                FileUtils.createParentDirectories(file);
                FileUtils.writeStringToFile(file, enVerifyInfo, StandardCharsets.UTF_8);
                log.error("register system success");
                return LicenseEnum.SUCCESS;
            } catch (Exception e) {
                log.error("register system, encrypt failed ,e: ", e);
                return LicenseEnum.LICENSE_FAIL;
            }
        }

        return LicenseEnum.LICENSE_INVALID;
    }

    @Override
    public void verifyDayAdd() {
        File verifyFile = new File(VERIFY_FILE_PATH);
        if (verifyFile.exists()) {
            try {
                String verifyString = FileUtils.readFileToString(verifyFile, "utf-8");
                String deVerifyString = AESUtil.decrypt(verifyString);
                JSONObject verifyJsonObject = JSON.parseObject(deVerifyString);
                Integer day = verifyJsonObject.containsKey("day") ? verifyJsonObject.getInteger("day") : 1;
                verifyJsonObject.put("day", ++day);
                String enVerifyInfo = AESUtil.encrypt(verifyJsonObject.toString());
                File file = new File(VERIFY_FILE_PATH);
                FileUtils.writeStringToFile(file, enVerifyInfo, StandardCharsets.UTF_8);
                log.info("当前已用{}天", day);
            } catch (Exception e) {
                log.error("e：{} ", e);
            }
        }
    }

    @Override
    public JSONObject getVerifyInfo() {
        JSONObject jsonObject = new JSONObject();
        File verifyFile = new File(VERIFY_FILE_PATH);
        if (!verifyFile.exists()) {
            jsonObject.put("errorCode", 1);
            log.error("系统验证码不存在，系统未注册");
        }
        try {
            String verifyString = FileUtils.readFileToString(verifyFile, "utf-8");
            String decrypt = AESUtil.decrypt(verifyString);
            jsonObject = JSON.parseObject(decrypt);
        } catch (Exception e) {
            jsonObject.put("errorCode", 2);
            log.error("read verify file {} failed , err {}", VERIFY_FILE_PATH, e);
        }
        return jsonObject;
    }

    @Override
    public Date getLicenseExpireTime() {
        JSONObject verifyJsonObject = getVerifyInfo();
        Long verifyTime = verifyJsonObject.getLong("time");
        Integer range = verifyJsonObject.getInteger("range");

        Calendar verifyCalendar = Calendar.getInstance();
        verifyCalendar.setTimeInMillis(verifyTime);
        verifyCalendar.add(Calendar.MONTH, range);
        return verifyCalendar.getTime();
    }
}
