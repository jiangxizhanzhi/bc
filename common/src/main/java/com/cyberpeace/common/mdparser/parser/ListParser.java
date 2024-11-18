package com.cyberpeace.common.mdparser.parser;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 列表解析器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see MdContentParser
 * @since 1.0.0
 */
@Service
public class ListParser implements MdContentParser<Collection, String> {

    /**
     * 解析
     *
     * @param content 内容
     * @return {@code List<String> }
     * @throws
     * @see Object
     * @see List<String>
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

        return StreamApiUtils.map(Arrays.asList(items), item -> StringUtils.isNotBlank(item) ? item.trim() : null);
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

        String value = "";
        for (Object o : content) {
            value = value + PatternConstant.MD_LIST
                    + (o instanceof String ? (String) o : JsonUtil.toJsonString(o))
                    + System.lineSeparator();
        }
        return LocalStringUtil.frontBackLine(value);
    }
}
