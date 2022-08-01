package com.cyberpeace.common.mdparser.parser;

import com.cyberpeace.common.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;

public class StringParser implements MdContentParser<String> {

	@Override
	public String parse(String content) {
		if (StringUtils.isBlank(content.trim())) {
			return CommonConstant.BLANK_STR;
		}
		return content.trim();
	}

}
