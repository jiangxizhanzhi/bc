package com.cyberpeace.common.mdparser.parser;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.mdparser.MdContentParser;
import com.cyberpeace.common.util.LocalStringUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class IntegerParser implements MdContentParser<Object, String> {

    @Override
    public Object parse(Object content) {

        String value = (String) content;
        if (StringUtils.isBlank(value.trim())) {
            return null;
        }

        return Integer.parseInt(value.trim());
    }

    @Override
    public String write(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return LocalStringUtil.frontBackLine(CommonConstant.EMPTY_STRING);
        }

        return LocalStringUtil.frontBackLine(String.valueOf(value));
    }
}
