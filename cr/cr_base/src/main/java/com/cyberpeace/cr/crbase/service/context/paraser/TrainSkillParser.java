package com.cyberpeace.cr.crbase.service.context.paraser;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.mdparser.MdContentParser;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.LocalStringUtil;

/**
 * 训练技能解析
 *
 * @author SN
 * @date 2024/11/13
 */
@Service
public class TrainSkillParser implements MdContentParser<Collection, String> {

	@Override
	public Collection parse(Object content) {
		return null;
	}

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
