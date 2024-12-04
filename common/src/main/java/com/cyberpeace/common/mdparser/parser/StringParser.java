package com.cyberpeace.common.mdparser.parser;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.LocalStringUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 字符串解析器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see MdContentParser
 * @since 1.0.0
 */
@Service
public class StringParser implements MdContentParser<String, String> {

    /**
     * 解析
     *
     * @param content 内容
     * @return {@code String }
     */
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

    /**
     * 写入
     *
     * @param content 内容
     * @return {@code String }
     */
    @Override
    public String write(String content) {
        return LocalStringUtil.frontBackLine(content);
    }
}
