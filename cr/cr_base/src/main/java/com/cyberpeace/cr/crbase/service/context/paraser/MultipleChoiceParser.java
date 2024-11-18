package com.cyberpeace.cr.crbase.service.context.paraser;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.mdparser.parser.MdContentParser;
import com.cyberpeace.common.util.LocalStringUtil;

/**
 * 多选题解析
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see MdContentParser
 * @since 1.0.0
 */
@Service
public class MultipleChoiceParser implements MdContentParser<String, String> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@code String }
	 * @throws
	 * @see Object
	 * @see String
	 */
	@Override
	public String parse(Object content) {
		return null;
	}

	/**
	 * 写入
	 *
	 * @param s s
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
	@Override
	public String write(String s) {
		String str = "";
		JSONArray options = JSONArray.parseArray(s);
		for (int i = 0; i < options.size(); i++) {
			str = str + "- " + options.getString(i) + System.lineSeparator();
		}
		return LocalStringUtil.frontBackLine(str);
	}
}
