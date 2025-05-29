package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间流量检测统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
/**
 * ### 代码分析： 
 * #### 类概述： 
 * - **类名**：`SpaceTrafficDetectRuleBo` 
 * - **功能**：这是一个用于表示空间流量检测规则的业务对象（BO），继承自 `SpaceBoBase`。 
 * - **注解**：使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * #### 属性字段： 
 * - **resourceId**：资源 ID，用于唯一标识资源。 
 * - **name**：规则的名称。 
 * - **ruleContent**：规则的具体内容。 
 * - **ruleId**：规则的编号。 
 * - **ruleInfo**：规则的详细信息。 
 * - **threatLevel**：威胁等级。 
 * - **tool**：使用的工具。 
 * - **stage**：防御阶段，类型为整数。 
 * - **attackType**：攻击类型，类型为整数。 
 * #### 方法： 
 * - **bo2Entity()**：将当前 BO 对象转换为实体对象 `SpaceTrafficDetectRule`。通过调用 `BeanUtils.copy()` 方法实现属性的拷贝。 
 * #### 依赖： 
 * - **BeanUtils**：用于对象之间的属性拷贝。 
 * - **SpaceTrafficDetectRule**：对应的实体类。 
 * - **SpaceBoBase**：父类，可能包含一些通用的属性或方法。 
 * #### 总结： 
 * 该类是一个典型的业务对象类，主要用于封装空间流量检测规则的相关信息，并提供方法将 BO 转换为实体对象。通过 Lombok 注解简化了代码量，提高了可读性和维护性。 
**/

@Getter
@Setter
public class SpaceTrafficDetectRuleBo extends SpaceBoBase {

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
	 * 规则内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "规则内容")
	private String ruleContent;

	/**
	 * 统治id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "规则编号")
	private String ruleId;

	/**
	 * 规则信息
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "规则信息")
	private String ruleInfo;

	/**
	 * 威胁层级
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "威胁等级")
	private String threatLevel;

	/**
	 * 工具
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "工具")
	private String tool;

	/**
	 * 阶段
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "防御阶段， type=57")
	private Integer stage;

	/**
	 * 攻击类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "攻击类型， type=58")
	private Integer attackType;

	/**
	 * BO2 实体
	 *
	 * @return 空间流量检测统治
	 * @throws
	 * @see SpaceTrafficDetectRule
	 */
	public SpaceTrafficDetectRule bo2Entity() {
		SpaceTrafficDetectRule entity = BeanUtils.copy(this, SpaceTrafficDetectRule.class);
		return entity;
	}

}
    
