package com.cyberpeace.common.mdparser.parser;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.mdparser.MdContentParser;
import com.cyberpeace.common.util.LocalStringUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class StringParser implements MdContentParser<String, String> {

    @Override
    public String parse(Object content) {
        if (ObjectUtils.isEmpty(content)) {
            return null;
        }

        String value = (String) content;
        if (StringUtils.isBlank(value.trim())) {
            return CommonConstant.BLANK_STR;
        }
        return value.trim();
    }

    @Override
    public String write(String content) {
        return LocalStringUtil.frontBackLine(content);
    }
}
