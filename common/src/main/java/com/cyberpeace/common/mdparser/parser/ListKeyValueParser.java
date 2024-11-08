package com.cyberpeace.common.mdparser.parser;

import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 列表钥匙值解析 器
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
public class ListKeyValueParser implements MdContentParser<Map<String, String>, String> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@code Map<String, String> }
	 */
	@Override
	public Map<String, String> parse(Object content) {

		Map<String, String> map = Maps.newTreeMap();
		if (ObjectUtils.isEmpty(content)) {
			return map;
		}

		String parserValue = (String) content;
		String[] items = parserValue.split(PatternConstant.KNOWLEDGE_SPLIT_STR);

		List<String> valueList = StreamApiUtils.filter(Arrays.asList(items),
				item -> StringUtils.isNotBlank(item));

		for (int i = 0; i < valueList.size(); i++) {
			String key = valueList.get(i);
			int valueIndex = i++;
			String value = valueIndex >= valueList.size() ? "" : valueList.get(valueIndex);
			map.put(key, value);
		}

		return map;
	}

	/**
	 * 写入
	 *
	 * @param map 地图
	 * @return {@code String }
	 */
	@Override
	public String write(Map<String, String> map) {

		String value = "";
		for (Map.Entry<String, String> entry : map.entrySet()) {
			value += PatternConstant.MD_LIST + entry.getKey() + System.lineSeparator()
					+ PatternConstant.SECOND_MD_LIST + entry.getValue() + System.lineSeparator();
		}

		return LocalStringUtil.frontBackLine(value);
	}
}
