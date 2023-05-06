package com.cyberpeace.common.mdparser.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.common.util.StreamApiUtils;

/**
 * 列表解析器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
public class ListParser implements MdContentParser<Collection, String> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@link List}<{@link String}>
	 */
	@Override
	public List<String> parse(Object content) {

		if (ObjectUtils.isEmpty(content)) {
			return Lists.newArrayList();
		}

		String value = (String) content;
		if (StringUtils.isBlank(value.trim())) {
			return Lists.newArrayList();
		}

		String[] items = value.split(PatternConstant.MD_LIST);
		List<String> itemList = StreamApiUtils.map(Arrays.asList(items),
				item -> StringUtils.isNotBlank(item) ? item.trim() : null);

		return itemList;
	}

	/**
	 * 写
	 *
	 * @param content 内容
	 * @return {@link String}
	 */
	@Override
	public String write(Collection content) {

		if (ObjectUtils.isEmpty(content)) {
			return LocalStringUtil.frontBackLine(CommonConstant.EMPTY_STRING);
		}

		if (CollectionUtils.isEmpty(content)) {
			return LocalStringUtil.frontBackLine(CommonConstant.EMPTY_STRING);
		}

		String value = "";
		for (Object o : content) {
			value = value + PatternConstant.MD_LIST
					+ (o instanceof String ? (String) o : JsonUtil.toJsonString(o))
					+ System.lineSeparator();
		}
		return LocalStringUtil.frontBackLine(value);
	}
}
