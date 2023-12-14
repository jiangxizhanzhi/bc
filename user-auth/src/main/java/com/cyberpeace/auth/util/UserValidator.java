package com.cyberpeace.auth.util;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

public class UserValidator {
    public static void validatePassword(String pwd){
        if (StringUtils.isBlank(pwd) || pwd.length() < 6){
            throw new BusinessException(ErrorCode.ILLEGAL_PWD);
        }
    }
}
