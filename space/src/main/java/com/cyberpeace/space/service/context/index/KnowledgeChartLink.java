package com.cyberpeace.space.service.context.index;

import lombok.Getter;
import lombok.Setter;

/**
 * 知识点图表链接
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `KnowledgeChartLink` 的 Java 类，用于表示知识点图表中的链接。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于存储两个字符串类型的属性：`source` 和 `target`，分别表示链接的源和目标。 
 * - 它可以用于构建知识点之间的关系图。 
 * 2. **注解与特性**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成了 `source` 和 `target` 属性的 getter 和 setter 方法，简化了代码。 
 * 3. **构造方法**： 
 * - 提供了一个构造方法 `KnowledgeChartLink(String source, String target)`，用于初始化对象的 `source` 和 `target` 属性。 
 * 4. **文档注释**： 
 * - 类和构造方法都有详细的 Javadoc 注释，描述了类的功能、属性和方法的作用。 
 * 总结：这是一个简单的数据传输对象（DTO），用于表示知识点图表中的链接关系。 
**/
@Getter
@Setter
public class KnowledgeChartLink {

	/**
	 * 源
	 *
	 * @see String
	 */
	private String source;

	/**
	 * 目标
	 *
	 * @see String
	 */
	private String target;

	/**
	 * 知识点图表链接
	 *
	 * @param source 源
	 * @param target 目标
	 * @throws
	 * @see String
	 * @see String
	 */
	public KnowledgeChartLink(String source, String target) {
		this.source = source;
		this.target = target;
	}
}

