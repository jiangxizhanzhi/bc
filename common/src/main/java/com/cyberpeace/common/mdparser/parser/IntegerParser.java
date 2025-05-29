package com.cyberpeace.common.mdparser.parser;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.LocalStringUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 整数解析器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see MdContentParser
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `IntegerParser` 的类，它实现了 `MdContentParser` 接口，并用于解析和格式化整数值。 
 * ### 主要功能： 
 * 1. **解析方法 (`parse`)**: 
 * - 接收一个对象 `content`，假设其为字符串。 
 * - 如果字符串为空或仅包含空白字符，则返回 `null`。 
 * - 否则，将字符串去除首尾空格后转换为整数并返回。 
 * 2. **写入方法 (`write`)**: 
 * - 接收一个对象 `value`，如果值为空或无效，则返回一个带有前后横线的空字符串。 
 * - 否则，将值转换为字符串，并用前后横线包裹后返回。 
 * ### 关键点： 
 * - 使用了 Apache Commons Lang 的工具类 (`StringUtils`, `ObjectUtils`) 来处理空值和字符串操作。 
 * - 使用了自定义工具类 `LocalStringUtil` 来格式化输出字符串。 
 * - 该类被 Spring 容器管理为服务 (`@Service`)。 
 * ### 总结： 
 * 此代码实现了一个简单的整数解析器，能够处理字符串到整数的转换以及整数到格式化字符串的转换。 
**/
@Service
public class IntegerParser implements MdContentParser<Object, String> {

    /**
     * 解析
     *
     * @param content 内容
     * @return {@code Object }
     */
    @Override
    public Object parse(Object content) {

        String value = (String) content;
        if (StringUtils.isBlank(value.trim())) {
            return null;
        }

        return Integer.parseInt(value.trim());
    }

    /**
     * 写入
     *
     * @param value 值
     * @return {@code String }
     */
    @Override
    public String write(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return LocalStringUtil.frontBackLine(CommonConstant.EMPTY_STRING);
        }

        return LocalStringUtil.frontBackLine(String.valueOf(value));
    }
}

