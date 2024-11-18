package com.cyberpeace.common.mdparser.convert;

import com.cyberpeace.common.constant.CommonConstant;

/**
 * 不转换
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Enum
 * @see MdConvertEnum
 * @since 1.0.0
 */
public enum NoConvert implements MdConvertEnum<String, String> {
    NO_CONVERT();

    /**
     * 自
     *
     * @return {@code String }
     * @throws
     * @see String
     */
    @Override
    public String to() {
        return CommonConstant.BLANK_STR;
    }

    /**
     * re 更改为
     *
     * @return {@code String }
     * @throws
     * @see String
     */
    @Override
    public String reTo() {
        return CommonConstant.BLANK_STR;
    }

}
