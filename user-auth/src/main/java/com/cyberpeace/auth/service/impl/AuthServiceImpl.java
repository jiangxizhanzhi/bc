package com.cyberpeace.auth.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.auth.constant.LicenseEnum;
import com.cyberpeace.auth.constant.SystemModelEnum;
import com.cyberpeace.auth.service.AuthService;
import com.cyberpeace.auth.service.VerifyLicenseService;
import com.cyberpeace.auth.util.AESUtil;
import com.cyberpeace.auth.util.CommonUtil;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.EnumUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author :   SN
 * @date :   2023/12/15 16:45
 * @desc :
 **/
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private static final String VERIFY_FILE_PATH = ConfigUtil.getContextProperty("license.verify.file.path");

    @Autowired
    private VerifyLicenseService verifyLicenseService;

	@Override
	public String getSerialNumber() {

		String uuid = getSysSerialNumber();

		// 获取系统uuid前4段的值，用于后续的比较
		String uuid4 = uuid.substring(0, 19);

		// 3、尝试获取激活文件中的序列号，并与原始uuid的前4段进行比较，如果相同，则返回激活文件中的序列号
		try {
			JSONObject verifyJsonObject = getVerifyJson();
			String currentSerialNumber = verifyJsonObject.getString("serialNumber");
			String currentSerialNumber4 = currentSerialNumber.substring(0, 19);
			if (uuid4.equals(currentSerialNumber4)) {
				return currentSerialNumber;
			}
		} catch (Exception e) {
			log.error("e：{} ", e);
		}

		// 4、如果没有激活文件，或者激活文件中的序列号与原始uuid的前4段不相同，返回原始的20位uuid
		String currentSerialNumber = getVerifyJsonSerialNumber();
		if (currentSerialNumber != null && uuid4.equals(currentSerialNumber.substring(0, 19))) {
			return currentSerialNumber;
		}

		return uuid;
	}

    @Override
    public LicenseEnum verifySystemCode(String model) {

        JSONObject verifyJsonObject = getVerifyInfo(model);
        if (verifyJsonObject.containsKey("errorCode")) {
            log.error("errorCode: {}", verifyJsonObject.containsKey("errorCode"));
            return LicenseEnum.LICENSE_NOT_ACTIVATED;
        }

		// 判断系统序列号是否与系统硬件匹配
		String currentSerialNumber = getVerifyJsonSerialNumber();
        String modelSerialNumber = verifyJsonObject.getString("serialNumber");
		String uuid = getSysSerialNumber();
		if (!currentSerialNumber.contains(uuid.substring(0, 19))
				|| !modelSerialNumber.contains(uuid.substring(0, 19))) {
			log.error("system serial number not match");
			return LicenseEnum.SERIAL_NUMBER_EXCEPTION;
		}

        Long verifyTime = verifyJsonObject.getLong("time");
        Integer range = verifyJsonObject.getInteger("range");
        Integer codeRange = verifyJsonObject.getInteger("codeRange");
        Integer day = verifyJsonObject.containsKey("day") ? verifyJsonObject.getInteger("day") : 1;
        String modeled = verifyJsonObject.containsKey("model") ? verifyJsonObject.getString("model") : SystemModelEnum.MODEL_GN.getName();
        if (CommonUtil.verifyTimeValid(verifyTime, range) && day <= range * 30) {
            //在注册有效期内
            String code = verifyJsonObject.getString("code");
            String serialNumber = verifyJsonObject.getString("serialNumber");
            Integer integer = verifyLicenseService.verifyCode(serialNumber, codeRange, code, modeled);
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
     * @param model 激活模块
     * @return
     */
    @Override
    public LicenseEnum registerVerifyInfo(Integer timeRange, String verifyCode, String model) {

        String serialNumber = getSerialNumber();

		// 判断系统是否已被激活
        Boolean isActive = verifySystemCode(model).equals(LicenseEnum.SUCCESS);

        // 如果系统已被激活，校验激活码是否已经使用过，如果使用过，则抛出异常
		JSONObject verifyInfo = getVerifyJson();
		if (isActive && (verifyInfo.getJSONArray("historyCodeList").contains(verifyCode))) {
			log.error("code has been used : {}", verifyCode);
			throw new BusinessException(ErrorCode.USED_ACTIVATION_CODE);
		}

        // 如果没有激活且不存在verify.json文件，初始化该文件
        File verifyFile = new File(VERIFY_FILE_PATH + "verify.json");
        if (!isActive && !verifyFile.exists()) {

            JSONObject verifyInitInfo = new JSONObject();
            verifyInitInfo.put("serialNumber", serialNumber);
            verifyInitInfo.put("historySerialList", Lists.newArrayList());
            verifyInitInfo.put("historyCodeList", Lists.newArrayList());

            try {
                String enVerifyInitInfo = AESUtil.encrypt(verifyInitInfo.toString());
                FileUtils.createParentDirectories(verifyFile);
                FileUtils.writeStringToFile(verifyFile, enVerifyInitInfo, StandardCharsets.UTF_8);
                log.info("Init verify file success");
            } catch (Exception e) {
                log.error("Init verify file failed ,e: ", e);
                throw new BusinessException(ErrorCode.ACTIVATION_FAILED);
            }

        }

        // 校验激活码是否有效并存储相关信息
        Integer verifyResult = verifyLicenseService.verifyCode(serialNumber, timeRange, verifyCode, model);
        if (LicenseEnum.SUCCESS.getCode().equals(verifyResult)) {

            Long activeTime = System.currentTimeMillis();
            Integer codeRange = timeRange;
            
            // 如果系统处于已被激活状态，提取激活时间和有效期，重新生成verify.json文件
			if (Boolean.TRUE.equals(isActive)) {
                Integer oldRange = getVerifyInfo(model).getInteger("range");
                timeRange = oldRange + timeRange;
                activeTime = getVerifyInfo(model).getLong("time");
            }

            // 构建新的verify.json文件
            JSONObject verifyJsonObject = new JSONObject();
            verifyJsonObject.put("time", activeTime);
            verifyJsonObject.put("range", timeRange);
            verifyJsonObject.put("codeRange", codeRange);
            verifyJsonObject.put("code", verifyCode);
            verifyJsonObject.put("model", model);
            verifyJsonObject.put("day", 1);
            verifyJsonObject.put("serialNumber", serialNumber);

            try {
                String enVerifyInfo = AESUtil.encrypt(verifyJsonObject.toString());
                File file = new File(VERIFY_FILE_PATH + model + "_" + "verify.json");
                FileUtils.createParentDirectories(file);
                FileUtils.writeStringToFile(file, enVerifyInfo, StandardCharsets.UTF_8);

                // 更新verify.json文件
                updateVerifyInfo(serialNumber, verifyCode);
                log.info("register system success");
                return LicenseEnum.SUCCESS;
            } catch (Exception e) {
                log.error("register system, encrypt failed ,e: ", e);
                throw new BusinessException(ErrorCode.ACTIVATION_FAILED);
            }
        }

        throw new BusinessException(ErrorCode.INVALID_ACTIVATION_CODE);
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
    public JSONObject getVerifyInfo(String model) {
        JSONObject jsonObject = new JSONObject();
        File verifyFile = new File(VERIFY_FILE_PATH + model + "_" + "verify.json");
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
	public Date getLicenseExpireTime(String model) {
		try {
			JSONObject verifyJsonObject = getVerifyInfo(model);
			Long verifyTime = verifyJsonObject.getLong("time");
			Integer range = verifyJsonObject.getInteger("range");
			Calendar verifyCalendar = Calendar.getInstance();
			verifyCalendar.setTimeInMillis(verifyTime);
			verifyCalendar.add(Calendar.MONTH, range);
			return verifyCalendar.getTime();
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.SYSTEM_NOT_REGISTERED);
		}
	}

	private JSONObject getVerifyJson() {

		File verifyFile = new File(VERIFY_FILE_PATH + "verify.json");
		if (verifyFile.exists()) {
			try {
				String verifyString = FileUtils.readFileToString(verifyFile, "utf-8");
				String deVerifyString = AESUtil.decrypt(verifyString);
				JSONObject verifyJsonObject = JSON.parseObject(deVerifyString);
				return verifyJsonObject;
			} catch (Exception e) {
				log.error("e：{} ", e);
				throw new BusinessException(ErrorCode.READ_ACTIVATION_FILE_FAILED);
			}
		}
		return null;
	}

	private String getVerifyJsonSerialNumber() {
		try {
			JSONObject verifyJsonObject = getVerifyJson();
			if (verifyJsonObject == null) {
				return null;
			}
			return verifyJsonObject.getString("serialNumber");
		} catch (Exception e) {
			log.error("e：{} ", e);
			throw new BusinessException(ErrorCode.READ_ACTIVATION_FILE_FAILED);
		}
	}

	private String getSysSerialNumber() {

		// 1、通过dmidecode获取系统原始uuid
		String uuid = CommonUtil.getSystemUuid();

		// 如果获取失败，抛出异常
		if (null == uuid) {
			throw new BusinessException(ErrorCode.GET_SYSTEM_UUID_FAILED);
		}

		// 2、处理原始uuid，去除-，取前20位，并重新使用-分成5段
		uuid = uuid.replace("-", "");
		uuid = uuid.substring(16, 20) + "-" + uuid.substring(20, 24) + "-" + uuid.substring(24, 28)
				+ "-" + uuid.substring(28, 32) + "-" + uuid.substring(12, 16);

		return uuid;

	}

	private Boolean updateVerifyInfo(String serialNumber, String verifyCode) {

		File verifyFile = new File(VERIFY_FILE_PATH + "verify.json");

		try {
			JSONObject verifyJsonObject = getVerifyJson();
			verifyJsonObject.getJSONArray("historySerialList").add(serialNumber);
			verifyJsonObject.getJSONArray("historyCodeList").add(verifyCode);

			// 替换序列号的后四位为随机四位字母
			String randomStr = UUID.randomUUID().toString().substring(0, 4);
			verifyJsonObject.put("serialNumber", serialNumber.substring(0, 19) + '-' + randomStr);

			String enVerifyInitInfo = AESUtil.encrypt(verifyJsonObject.toString());
			FileUtils.createParentDirectories(verifyFile);
			FileUtils.writeStringToFile(verifyFile, enVerifyInitInfo, StandardCharsets.UTF_8);
			log.info("Update verify file success");
		} catch (Exception e) {
			log.error("Update verify file failed ,e: ", e);
			throw new BusinessException(ErrorCode.ACTIVATION_FAILED);
		}

		return Boolean.TRUE;

	}
}
