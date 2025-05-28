/**
 * 该代码定义了一个名为 `StringParser` 的类，实现了 `MdContentParser<String, String>` 接口。以下是代码的主要功能和特点： 
 * 1. **功能描述**： 
 * - `StringParser` 是一个字符串解析器，主要用于处理输入内容并返回格式化的字符串。 
 * - 提供了两个核心方法：`parse` 和 `write`。 
 * 2. **`parse` 方法**： 
 * - 参数 `content` 是需要解析的对象，可以是任意类型。 
 * - 如果 `content` 为 `null` 或空字符串（包括空白字符），返回 `null` 或默认空字符串 `CommonConstant.BLANK_STR`。 
 * - 否则，返回去除前后空白后的字符串。 
 * 3. **`write` 方法**： 
 * - 参数 `content` 是需要写入格式化的字符串。 
 * - 调用 `LocalStringUtil.frontBackLine` 方法对字符串进行格式化处理，并返回结果。 
 * 4. **依赖与工具**： 
 * - 使用了 Apache Commons Lang 的 `ObjectUtils` 和 `StringUtils` 工具类来判断对象是否为空或字符串是否为空白。 
 * - 引用了 `CommonConstant.BLANK_STR` 表示默认空字符串。 
 * - 使用了 `LocalStringUtil.frontBackLine` 方法用于字符串格式化。 
 * 5. **设计目的**： 
 * - 该类旨在提供一种通用的字符串解析和格式化能力，适用于需要处理文本内容的场景。 
 * 总结来说，`StringParser` 是一个简单的字符串处理器，主要用于清理和格式化输入字符串，符合单一职责原则，适合在需要字符串操作的模块中使用。 
**/
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
