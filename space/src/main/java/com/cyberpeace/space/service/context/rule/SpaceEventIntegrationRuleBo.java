/**
 * 这段代码定义了一个名为 `SpaceEventIntegrationRuleBo` 的类，它是 `SpaceBoBase` 的子类。该类主要用于表示空间事件集成规则的业务对象（BO），并提供了将自身转换为实体对象的方法。 
 * ### 主要功能分析： 
 * 1. **属性定义**： 
 * - 该类包含多个属性，如 `resourceId`, `type`, `description`, `content`, `tool`, 和 `stage`。 
 * - 每个属性都有对应的注释说明其用途，并且使用了 `@ApiModelProperty` 注解来描述 API 文档中的字段信息。 
 * 2. **继承关系**： 
 * - 继承自 `SpaceBoBase`，表明它可能包含了基类的一些通用方法或属性。 
 * 3. **方法实现**： 
 * - 提供了一个 `bo2Entity()` 方法，用于将当前业务对象转换为目标实体类 `SpaceEventIntegrationRule`。 
 * - 使用了 `BeanUtils.copy()` 方法来完成对象之间的属性拷贝操作。 
 * ### 总结： 
 * 这个类的设计目的是为了在业务逻辑层和服务层之间传递数据模型，并提供一种方式将这些业务对象转换成数据库可以存储的实体对象。通过这样的设计，可以保持业务逻辑与数据访问层分离，便于维护和扩展。 
**/

package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间事件集成统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceEventIntegrationRuleBo extends SpaceBoBase {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "检测类型")
	private String type;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容简介")
	private String description;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容详情")
	private String content;

	/**
	 * 工具
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "应用工具")
	private String tool;

	/**
	 * 阶段
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "防御阶段， type=57")
	private Integer stage;

	/**
	 * BO2 实体
	 *
	 * @return 空间事件集成规则
	 * @throws
	 * @see SpaceEventIntegrationRule
	 */
	public SpaceEventIntegrationRule bo2Entity() {
		SpaceEventIntegrationRule entity = BeanUtils.copy(this, SpaceEventIntegrationRule.class);
		return entity;
	}

}
    