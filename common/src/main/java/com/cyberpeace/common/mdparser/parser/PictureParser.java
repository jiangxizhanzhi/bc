/**
 * 这段代码定义了一个名为 `PictureParser` 的类，它实现了 `MdContentParser<String, String>` 接口，并被标注为 Spring 容器中的一个服务组件。以下是代码的主要功能和结构分析： 
 * 1. **类的功能**： 
 * - `PictureParser` 是一个图片解析器，主要用于处理 Markdown 格式的图片内容。 
 * - 它提供了两个主要方法：`parse` 和 `write`。 
 * 2. **方法分析**： 
 * - **`parse` 方法**： 
 * - 当前实现返回 `null`，表明该方法尚未具体实现，可能用于将某种内容解析为图片相关的格式。 
 * - **`write` 方法**： 
 * - 该方法接收一个字符串 `content`，如果 `content` 为空或空白，则返回空字符串。 
 * - 否则，它使用工具类 `LocalStringUtil` 将 `content` 包装成 Markdown 图片语法（如 `![](url)`），并返回结果。 
 * 3. **依赖与工具类**： 
 * - 使用了 `org.apache.commons.lang3.StringUtils` 检查字符串是否为空或空白。 
 * - 使用了自定义工具类 `com.cyberpeace.common.util.LocalStringUtil` 来生成 Markdown 图片语法。 
 * 4. **注解与上下文**： 
 * - 类被标注为 `@Service`，意味着它是一个 Spring 管理的服务类，可以在 Spring 应用中通过依赖注入使用。 
 * 总结来说，这个类的核心功能是将输入内容包装为 Markdown 图片语法，但其 `parse` 方法目前未实现具体逻辑。 
**/
package com.cyberpeace.common.mdparser.parser;

import com.cyberpeace.common.util.LocalStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 图片解析器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see MdContentParser
 * @since 1.0.0
 */
@Service
public class PictureParser implements MdContentParser<String, String> {

	/**
	 * 解析
	 *
	 * @param content 内容
	 * @return {@code String }
	 */
	@Override
	public String parse(Object content) {
		return null;
	}

	/**
	 * 写入
	 *
	 * @param content 内容
	 * @return {@code String }
	 */
	@Override
	public String write(String content) {
		if(StringUtils.isBlank(content)){
			return "";
		}
		return LocalStringUtil.frontBackLine("![](" + content + ")");
	}
}
