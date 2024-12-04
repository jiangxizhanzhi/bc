package com.cyberpeace.common.mdparser.parser;

import com.cyberpeace.common.util.LocalStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 图片解析器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see MdContentParser
 * @since 1.0.0
 */
@Service
public class PictureParser implements MdContentParser<String, String> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@code String }
	 */
	@Override
	public String parse(Object content) {
		return null;
	}

	/**
	 * 写入
	 *
	 * @param content 内容
	 * @return {@code String }
	 */
	@Override
	public String write(String content) {
		if(StringUtils.isBlank(content)){
			return "";
		}
		return LocalStringUtil.frontBackLine("![](" + content + ")");
	}
}
