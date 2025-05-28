/**
 * 这段代码定义了一个名为 `SpaceRoundMechanismRuleBo` 的 Java 类，继承自 `SpaceBoBase`。以下是代码的主要功能和结构分析： 
 * 1. **类的定义**： 
 * - 类名：`SpaceRoundMechanismRuleBo` 
 * - 包名：`com.cyberpeace.space.service.context.rule` 
 * - 功能描述：该类是一个业务对象（BO），用于表示与“空间圆机制规则”相关的数据。 
 * 2. **继承关系**： 
 * - 继承自 `SpaceBoBase`，表明它可能包含一些通用的基础属性或方法。 
 * 3. **注解**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述信息，便于生成 API 文档。 
 * 4. **字段说明**： 
 * - `resourceId`：资源 ID，字符串类型。 
 * - `name`：名称，字符串类型。 
 * - `description`：描述，字符串类型。 
 * - `roundLength`：圆环长度（即每轮时长），整数类型。 
 * - `initScore`：初始化分数，整数类型。 
 * - `maxResetTime`：最大重置次数，整数类型。 
 * - `minResetInterval`：最小重置间隔，整数类型。 
 * 5. **方法说明**： 
 * - `bo2Entity()`：将当前对象转换为 `SpaceRoundMechanismRule` 实体对象的方法。使用了工具类 `BeanUtils.copy` 来完成对象之间的拷贝操作。 
 * 6. **设计意图**： 
 * - 这个类主要用于封装与“空间圆机制规则”相关的信息，并提供一个方法将其转换为数据库实体对象，便于持久化操作。 
 * 总结来说，这段代码实现了一个业务对象类，用于管理和传递“空间圆机制规则”的相关信息，并且通过工具类简化了对象转换的过程。 
**/

package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceRoundMechanismRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间圆机制统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceRoundMechanismRuleBo extends SpaceBoBase {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;


	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "简述")
	private String description;

	/**
	 * 圆环长度
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "每轮时长")
	private Integer roundLength;

	/**
	 * 初始化分数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "初始分值")
	private Integer initScore;

	/**
	 * 最大重置时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "重置最大次数")
	private Integer maxResetTime;

	/**
	 * 最小重置间隔
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "重置最小间隔")
	private Integer minResetInterval;

	/**
	 * BO2 实体
	 *
	 * @return SpaceRound机构规则
	 * @throws
	 * @see SpaceRoundMechanismRule
	 */
	public SpaceRoundMechanismRule bo2Entity() {
		SpaceRoundMechanismRule entity = BeanUtils.copy(this, SpaceRoundMechanismRule.class);
		return entity;
	}

}
    