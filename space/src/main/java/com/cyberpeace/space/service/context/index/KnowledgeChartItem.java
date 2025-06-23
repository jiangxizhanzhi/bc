package com.cyberpeace.space.service.context.index;

import com.cyberpeace.space.constant.SpaceKTypeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 知识点图表项目
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `KnowledgeChartItem` 的 Java 类，用于表示知识图谱中的图表项目。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于构建知识图谱中的图表项，包含名称、ID、类别、类型、符号大小和值等属性。 
 * 2. **属性说明**： 
 * - `name`: 图表项的名称。 
 * - `id`: 图表项的唯一标识符。 
 * - `category`: 图表项所属的类别。 
 * - `type`: 图表项的类型，通过枚举 `SpaceKTypeEnum` 定义。 
 * - `symbolSize`: 图表项的符号大小，根据层级计算得出。 
 * - `value`: 图表项的值，默认为 0。 
 * 3. **构造方法**： 
 * - 提供了两个构造方法： 
 * - 第一个构造方法接受 `SpaceKTypeEnum`、父 ID 和层级作为参数，初始化所有属性。 
 * - 第二个构造方法仅接受层级参数，用于初始化符号大小。 
 * 4. **辅助方法**： 
 * - `calSymbolSize(int level)`: 根据层级计算符号大小，公式为 `(64 >> level) + 4`，其中 `>>` 是右移运算符，表示将数值向右移动指定的位数。 
 * 5. **注解与设计**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，用于描述 API 文档中的字段。 
 * 6. **用途**： 
 * - 该类适用于需要动态生成知识图谱图表的场景，特别是基于层级结构的数据展示。 
 * 总体而言，这个类的设计简洁明了，适合用于知识图谱的可视化需求。 
**/
@Getter
@Setter
public class KnowledgeChartItem {

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
	 * 类别
	 *
	 * @see String
	 */
	private String category;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;

	/**
	 * 元件大小
	 *
	 * @see Integer
	 */
	private Integer symbolSize;

	/**
	 * 值
	 *
	 * @see Integer
	 */
	private Integer value;

	/**
	 * 知识点图表项目
	 *
	 * @param typeEnum 类型 enum
	 * @param parentId 父母id
	 * @param level    层级
	 * @throws
	 * @see SpaceKTypeEnum
	 * @see String
	 * @see int
	 */
	public KnowledgeChartItem(SpaceKTypeEnum typeEnum, String parentId, int level) {
		this.name = typeEnum.getName();
		this.id = typeEnum.getId();
		this.category = parentId;
		this.type = typeEnum.getType();
		this.symbolSize = this.calSymbolSize(level);
		this.value = 0;
	}

	/**
	 * 知识点图表项目
	 *
	 * @param level 层级
	 * @throws
	 * @see int
	 */
	public KnowledgeChartItem(int level){
		this.symbolSize = calSymbolSize(level);
	}

	/**
	 * CAL 符号大小
	 *
	 * @param level 层级
	 * @return int
	 * @throws
	 * @see int
	 * @see int
	 */
	private int calSymbolSize(int level) {
		// 确保level在合理范围内
		if (level < 0 || level > 6) {
			throw new IllegalArgumentException("Level must be between 0 and 6");
		}
		return (64 >> level) + 4;
	}
}

