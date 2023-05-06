package com.cyberpeace.common.mdparser.convert;

import com.cyberpeace.common.constant.CommonConstant;

/**
 * 没有转换
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
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
