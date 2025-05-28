/**
 * 该代码定义了一个泛型接口 `MdContentParser`，用于解析和写入 Markdown 内容。以下是简要分析： 
 * 1. **接口定义**： 
 * - 接口名为 `MdContentParser`，表示用于处理 Markdown 内容的解析器。 
 * - 使用了泛型 `<T, R>`，允许返回值类型灵活定义。 
 * 2. **方法说明**： 
 * - `parse(Object content)`：接收一个 `Object` 类型的内容参数，返回泛型类型 `T`，用于解析 Markdown 内容。 
 * - `write(T t)`：接收泛型类型 `T` 参数，返回泛型类型 `R`，用于将解析后的结果写入或转换为其他形式。 
 * 3. **用途**： 
 * - 该接口适用于需要对 Markdown 内容进行解析和生成的场景，例如 Markdown 到 HTML 的转换或其他格式的输出。 
 * 4. **设计特点**： 
 * - 泛型设计提高了接口的灵活性，适合多种数据类型的解析和写入。 
 * - 方法分离了解析和写入逻辑，便于模块化实现。 
 * 总结：这是一个通用的 Markdown 内容解析器接口，支持自定义解析和写入逻辑，适用于多种应用场景。 
**/
package com.cyberpeace.common.mdparser.parser;

/**
 * MD 内容解析器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface MdContentParser<T, R> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@code T }
	 */
	T parse(Object content);

	/**
	 * 写入
	 *
	 * @param t t
	 * @return {@code R }
	 */
	R write(T t);

}
