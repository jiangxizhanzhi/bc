/**
 * 这段代码定义了一个名为 `ListParser` 的类，用于解析和生成 Markdown 列表内容。以下是对其功能和结构的简要分析： 
 * ### 功能概述 
 * 1. **解析功能**： 
 * - 方法 `parse(Object content)` 接收一个对象作为输入，将其转换为字符串，并根据特定的分隔符（定义在 `PatternConstant.MD_LIST` 中）分割成多个列表项。 
 * - 返回一个包含这些列表项的字符串列表。 
 * 2. **生成功能**： 
 * - 方法 `write(Collection content)` 接收一个集合对象，将其中的每个元素格式化为 Markdown 列表项，并返回完整的 Markdown 列表字符串。 
 * ### 关键点分析 
 * - **依赖注入与工具类**： 
 * - 使用了多个 Apache Commons 和 Spring 框架提供的工具类（如 `ObjectUtils`, `StringUtils`, `StreamApiUtils` 等），简化了空值检查、字符串操作和流式处理。 
 * - **接口实现**： 
 * - 实现了泛型接口 `MdContentParser<Collection, String>`，确保了方法签名的一致性。 
 * - **边界条件处理**： 
 * - 在解析和生成过程中都进行了严格的空值检查，确保即使输入为空或无效也能正常工作。 
 * ### 总结 
 * 该类通过标准化的方式实现了 Markdown 列表的解析与生成，具有良好的可读性和扩展性。通过利用现有的工具类，减少了冗余代码，提高了开发效率。 
**/
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
 * @date 2024/11/08
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
