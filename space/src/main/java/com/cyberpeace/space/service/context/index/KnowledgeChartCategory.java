package com.cyberpeace.space.service.context.index;

import com.cyberpeace.space.constant.SpaceKTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 知识点图表类别
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `KnowledgeChartCategory` 的 Java 类，用于表示知识点图表类别。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于封装知识点图表类别的信息，包括名称 (`name`) 和 ID (`id`)。 
 * - 提供了两种构造方法：一种通过枚举类型 `SpaceKTypeEnum` 初始化，另一种直接通过字符串参数初始化。 
 * 2. **字段**： 
 * - `name`: 表示知识点图表类别的名称。 
 * - `id`: 表示知识点图表类别的唯一标识符。 
 * 3. **构造方法**： 
 * - 第一个构造方法接收 `SpaceKTypeEnum` 类型的参数，从枚举中提取名称和 ID 并赋值给类的字段。 
 * - 第二个构造方法允许直接传入名称和 ID，用于更灵活的初始化。 
 * 4. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成了字段的 getter 和 setter 方法，简化了代码。 
 * 5. **用途**： 
 * - 该类可以作为知识点图表类别数据模型，便于在应用程序中传递和使用。 
 * 总体来说，这是一个简单的数据模型类，设计目的是为了方便管理和操作知识点图表类别信息。 
**/
@Getter
@Setter
public class KnowledgeChartCategory {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	/**
	 * 知识点图表类别
	 *
	 * @param typeEnum 类型 enum
	 * @throws
	 * @see SpaceKTypeEnum
	 */
	public KnowledgeChartCategory(SpaceKTypeEnum typeEnum) {
		this.name = typeEnum.getName();
		this.id = typeEnum.getId();
	}

	/**
	 * 知识点图表类别
	 *
	 * @param name 名称
	 * @param id   id
	 * @throws
	 * @see String
	 * @see String
	 */
	public KnowledgeChartCategory(String name, String id) {
		this.name = name;
		this.id = id;
	}
}

