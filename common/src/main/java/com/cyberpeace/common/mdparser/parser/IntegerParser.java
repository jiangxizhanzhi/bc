package com.cyberpeace.common.mdparser.parser;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.LocalStringUtil;

/**
 * 整数解析器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
public class IntegerParser implements MdContentParser<Object, String> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@link Object}
	 */
	@Override
	public Object parse(Object content) {

		String value = (String) content;
		if (StringUtils.isBlank(value.trim())) {
			return null;
		}

		return Integer.parseInt(value.trim());
	}

	/**
	 * 写
	 *
	 * @param value 价值
	 * @return {@link String}
	 */
	@Override
	public String write(Object value) {
		if (ObjectUtils.isEmpty(value)) {
			return LocalStringUtil.frontBackLine(CommonConstant.EMPTY_STRING);
		}

		return LocalStringUtil.frontBackLine(String.valueOf(value));
	}
}
