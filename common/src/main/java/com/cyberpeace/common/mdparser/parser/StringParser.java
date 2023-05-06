package com.cyberpeace.common.mdparser.parser;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.LocalStringUtil;

/**
 * 字符串解析器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
public class StringParser implements MdContentParser<String, String> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@link String}
	 */
	@Override
	public String parse(Object content) {
		if (ObjectUtils.isEmpty(content)) {
			return null;
		}

		String value = (String) content;
		if (StringUtils.isBlank(value.trim())) {
			return CommonConstant.BLANK_STR;
		}
		return value.trim();
	}

	/**
	 * 写
	 *
	 * @param content 内容
	 * @return {@link String}
	 */
	@Override
	public String write(String content) {
		return LocalStringUtil.frontBackLine(content);
	}
}
