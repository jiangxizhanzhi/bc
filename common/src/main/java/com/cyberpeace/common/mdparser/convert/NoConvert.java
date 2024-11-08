package com.cyberpeace.common.mdparser.convert;

import com.cyberpeace.common.constant.CommonConstant;

/**
 * 不转换
 *
 * @author SN
 * @date 2024/11/08
 */
public enum NoConvert implements MdConvertEnum<String, String> {
    NO_CONVERT();

    /**
     * 自
     *
     * @return {@code String }
     */
    @Override
    public String to() {
        return CommonConstant.BLANK_STR;
    }

    /**
     * re 更改为
     *
     * @return {@code String }
     */
    @Override
    public String reTo() {
        return CommonConstant.BLANK_STR;
    }

}
