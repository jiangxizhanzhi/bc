package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号统治
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `IndexRule` 的 Java 类，位于 `com.cyberpeace.space.service.context.index` 包中。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `IndexRule` 是一个用于管理编号规则的类。 
 * - 它包含多个整型字段，分别表示不同的规则值（如基础、警告、威胁、比赛等），并提供了一个方法来计算这些值的总和。 
 * 2. **字段说明**： 
 * - `base`: 表示基础数据规则，默认值为 0。 
 * - `warning`: 表示告警字典规则，默认值为 0。 
 * - `threat`: 表示威胁防御规则，默认值为 0。 
 * - `race`: 表示比赛及训练评估规则，默认值为 0。 
 * - `sum`: 表示总数，默认值为 0。 
 * - `use`: 表示使用次数，默认值为 0。 
 * 3. **方法说明**： 
 * - `calSum()`: 计算 `sum` 字段的值，将其设置为 `base`、`warning`、`race` 和 `threat` 字段的总和。 
 * 4. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供了描述信息。 
 * 5. **设计特点**： 
 * - 类的设计简单直观，便于管理和计算不同规则的总和。 
 * - 默认值的设置使得对象在初始化时具有合理的初始状态。 
 * 总体而言，这个类是一个简单的数据结构类，主要用于存储和计算与编号规则相关的数据。 
**/
@Getter
@Setter
public class IndexRule {

	/**
	 * 基础
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "基础数据规则")
	private Integer base = 0;

	/**
	 * 警告
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "告警字典规则")
	private Integer warning = 0;

	/**
	 * 威胁
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "威胁防御规则")
	private Integer threat = 0;

	/**
	 * 比赛
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "比赛及训练评估规则")
	private Integer race = 0;

	/**
	 * 和
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "总数")
	private Integer sum = 0;

	/**
	 * 使用
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "使用次数")
	private Integer use = 0;

	/**
	 * Cal Sum
	 *
	 * @throws
	 */
	public void calSum() {
		this.sum = this.base + this.warning + this.race + this.threat;
	}
}

