package com.cyberpeace.common.mdparser.parser;

import com.cyberpeace.common.mdparser.MdContentParser;
import com.cyberpeace.common.util.LocalStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class PictureParser implements MdContentParser<String, String> {

	@Override
	public String parse(Object content) {
		return null;
	}

	@Override
	public String write(String content) {
		if(StringUtils.isBlank(content)){
			return "";
		}
		return LocalStringUtil.frontBackLine("![](" + content + ")");
	}
}
