package com.cyberpeace.cr.crbase.service.context.paraser;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.mdparser.parser.MdContentParser;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.LocalStringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 训练技能解析
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
public class TrainSkillParser implements MdContentParser<Collection, String> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@code Collection }
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
