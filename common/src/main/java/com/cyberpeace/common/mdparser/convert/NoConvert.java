package com.cyberpeace.common.mdparser.convert;

import com.cyberpeace.common.constant.CommonConstant;

public enum NoConvert implements MdConvertEnum<String, String> {
    NO_CONVERT();

    @Override
    public String to() {
        return CommonConstant.BLANK_STR;
    }

    @Override
    public String reTo() {
        return CommonConstant.BLANK_STR;
    }

}
