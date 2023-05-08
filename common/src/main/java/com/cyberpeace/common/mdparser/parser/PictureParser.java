package com.cyberpeace.common.mdparser.parser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.LocalStringUtil;

/**
 * 图片解析器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
public class PictureParser implements MdContentParser<String, String> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@link String}
	 */
	@Override
	public String parse(Object content) {
		return null;
	}

	/**
	 * 写
	 *
	 * @param content 内容
	 * @return {@link String}
	 */
	@Override
	public String write(String content) {
		if (StringUtils.isBlank(content)) {
			return "";
		}
		return LocalStringUtil.frontBackLine("![](" + content + ")");
	}
}
