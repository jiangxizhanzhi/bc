package com.cyberpeace.cr.crbase.service.context.paraser;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.mdparser.parser.MdContentParser;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.LocalStringUtil;

/**
 * 训练技能解析
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see MdContentParser
 * @since 1.0.0
 */
@Service
public class TrainSkillParser implements MdContentParser<Collection, String> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@code Collection }
	 * @throws
	 * @see Object
	 * @see Collection
	 */
	@Override
	public Collection parse(Object content) {
		return null;
	}

	/**
	 * 写入
	 *
	 * @param content 内容
	 * @return {@code String }
	 * @throws
	 * @see Collection
	 * @see String
	 */
	@Override
	public String write(Collection content) {
		if (ObjectUtils.isEmpty(content)) {
			return LocalStringUtil.frontBackLine(CommonConstant.EMPTY_STRING);
		}

		if (CollectionUtils.isEmpty(content)) {
			return LocalStringUtil.frontBackLine(CommonConstant.EMPTY_STRING);
		}

		String value = PatternConstant.MD_LIST + "DEFAULT" + System.lineSeparator();
		for (Object o : content) {
			value = value + "  " + PatternConstant.MD_LIST
					+ (o instanceof String ? (String) o : JsonUtil.toJsonString(o))
					+ System.lineSeparator();
		}
		return LocalStringUtil.frontBackLine(value);
	}
}
