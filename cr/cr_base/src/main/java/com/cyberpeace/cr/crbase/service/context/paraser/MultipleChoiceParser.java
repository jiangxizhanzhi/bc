package com.cyberpeace.cr.crbase.service.context.paraser;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.mdparser.parser.MdContentParser;
import com.cyberpeace.common.util.LocalStringUtil;

/**
 * 选择题解析器
 * 多选题解析
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
public class MultipleChoiceParser implements MdContentParser<String, String> {

	@Override
	public String parse(Object content) {
		return null;
	}

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
