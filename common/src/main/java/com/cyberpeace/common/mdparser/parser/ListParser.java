package com.cyberpeace.common.mdparser.parser;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.util.StreamApiUtils;

public class ListParser implements MdContentParser<List<String>> {

	@Override
	public List<String> parse(String content) {
		if (StringUtils.isBlank(content.trim())) {
			return Lists.newArrayList();
		}

		String[] items = content.split(PatternConstant.KNOWLEDGE_SPLIT_STR);
		List<String> itemList = StreamApiUtils.map(Arrays.asList(items),
				item -> StringUtils.isNotBlank(item) ? item.trim() : null);

		return itemList;
	}

}
