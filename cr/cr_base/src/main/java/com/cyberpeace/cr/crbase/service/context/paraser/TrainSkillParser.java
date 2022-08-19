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
