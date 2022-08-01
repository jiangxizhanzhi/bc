package com.cyberpeace.common.mdparser.parser;

import org.apache.commons.lang3.StringUtils;

public class IntegerParser implements MdContentParser<Integer> {

	@Override
	public Integer parse(String content) {
		if (StringUtils.isBlank(content.trim())) {
			return null;
		}

		return Integer.parseInt(content.trim());
	}

}
