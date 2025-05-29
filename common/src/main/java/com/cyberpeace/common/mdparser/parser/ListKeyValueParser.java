package com.cyberpeace.common.mdparser.parser;

import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 列表钥匙值解析 器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see MdContentParser
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 类概述 
 * - **类名**: `ListKeyValueParser` 
 * - **功能**: 实现了一个用于解析和生成列表键值对的解析器。 
 * - **注解**: 使用了 `@Service` 注解，表明这是一个 Spring 容器中的服务类。 
 * #### 接口实现 
 * - 实现了泛型接口 `MdContentParser<Map<String, String>, String>`： 
 * - `parse(Object content)` 方法将输入内容解析为 `Map<String, String>`。 
 * - `write(Map<String, String> map)` 方法将 `Map<String, String>` 转换为字符串形式。 
 * #### 核心方法分析 
 * ##### `parse(Object content)` 
 * - **参数**: 输入内容 `content`，类型为 `Object`。 
 * - **逻辑**: 
 * 1. 如果 `content` 为空或无效，则返回一个空的 `TreeMap`。 
 * 2. 将 `content` 转换为字符串，并按指定分隔符 (`PatternConstant.KNOWLEDGE_SPLIT_STR`) 分割成数组。 
 * 3. 过滤掉空值，得到有效的键值对列表。 
 * 4. 遍历过滤后的列表，将偶数索引作为键，奇数索引作为值，存入 `TreeMap`。 
 * - **注意**: 如果键值对数量不匹配（如键多于值），多余的键会被赋予空值。 
 * ##### `write(Map<String, String> map)` 
 * - **参数**: 输入的 `Map<String, String>`。 
 * - **逻辑**: 
 * 1. 遍历 `map` 的每个键值对。 
 * 2. 将键值对格式化为特定的 Markdown 格式，并拼接成字符串。 
 * 3. 调用 `LocalStringUtil.frontBackLine` 方法处理字符串的前后内容。 
 * - **输出**: 返回格式化的字符串。 
 * #### 工具类与常量 
 * - 使用了多个工具类和常量： 
 * - `PatternConstant`: 提供分隔符等常量。 
 * - `LocalStringUtil`: 提供字符串前后添加行的功能。 
 * - `StreamApiUtils`: 提供流式操作的工具方法。 
 * - `ObjectUtils` 和 `StringUtils`: Apache Commons 提供的工具类，用于对象和字符串的非空检查。 
 * #### 总结 
 * 该类主要用于处理列表键值对的解析和生成，适用于需要将结构化数据转换为 Markdown 格式的场景。代码逻辑清晰，使用了多种工具类和方法来简化操作，体现了良好的代码复用性和可维护性。 
**/
@Service
public class ListKeyValueParser implements MdContentParser<Map<String, String>, String> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@code Map<String, String> }
	 */
	@Override
	public Map<String, String> parse(Object content) {

		Map<String, String> map = Maps.newTreeMap();
		if (ObjectUtils.isEmpty(content)) {
			return map;
		}

		String parserValue = (String) content;
		String[] items = parserValue.split(PatternConstant.KNOWLEDGE_SPLIT_STR);

		List<String> valueList = StreamApiUtils.filter(Arrays.asList(items),
				item -> StringUtils.isNotBlank(item));

		for (int i = 0; i < valueList.size(); i++) {
			String key = valueList.get(i);
			int valueIndex = i++;
			String value = valueIndex >= valueList.size() ? "" : valueList.get(valueIndex);
			map.put(key, value);
		}

		return map;
	}

	/**
	 * 写入
	 *
	 * @param map 地图
	 * @return {@code String }
	 */
	@Override
	public String write(Map<String, String> map) {

		String value = "";
		for (Map.Entry<String, String> entry : map.entrySet()) {
			value += PatternConstant.MD_LIST + entry.getKey() + System.lineSeparator()
					+ PatternConstant.SECOND_MD_LIST + entry.getValue() + System.lineSeparator();
		}

		return LocalStringUtil.frontBackLine(value);
	}
}

