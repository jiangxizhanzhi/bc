package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间容易威胁检测统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceAptThreatDetectRuleBo` 的 Java 类，它继承自 `SpaceBoBase` 并用于表示空间APT威胁检测规则的业务对象（BO）。以下是代码的主要分析： 
 * 1. **类的结构与注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - Swagger 注解 `@ApiModelProperty` 用于描述每个字段的用途。 
 * 2. **字段说明**： 
 * - `resourceId`: 资源ID，用于唯一标识某个资源。 
 * - `name`: 名称，描述该规则的名称。 
 * - `type`: 类型，用整数值表示不同的类型（如IP、DNS等）。 
 * - `aptCode`: APT代号，用于标识特定的APT组织或活动。 
 * - `content`: 内容详情，提供详细信息。 
 * - `description`: 内容简介，简要描述。 
 * - `tool`: 工具，可能指用于检测的工具。 
 * 3. **方法功能**： 
 * - `bo2Entity()`: 将当前 BO 对象转换为实体类 `SpaceAptThreatDetectRule`。通过 `BeanUtils.copy` 方法实现对象的拷贝。 
 * 4. **设计意图**： 
 * - 该类作为数据传输对象（DTO），封装了空间APT威胁检测相关的业务逻辑和数据结构。 
 * - 提供了将业务对象转换为持久化实体的功能，便于数据库操作。 
 * 总体而言，这段代码清晰地定义了一个用于处理空间APT威胁检测规则的业务对象，并提供了必要的数据转换支持。 
**/

@Getter
@Setter
public class SpaceAptThreatDetectRuleBo extends SpaceBoBase {

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
	 * 类型
	 * 1:IP,2:DNS,3:URL,4:邮箱账号,5:文件HASH,6:证书HASH
	 * @see String
	 */
	@ApiModelProperty(value = "情报类型")
	private String type;

	/**
	 * 容易代号
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "APT代号")
	private String aptCode;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容详情")
	private String content;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容简介")
	private String description;

	/**
	 * 工具
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "工具")
	private String tool;

	/**
	 * BO2 实体
	 *
	 * @return SpaceAPT威胁检测规则
	 * @throws
	 * @see SpaceAptThreatDetectRule
	 */
	public SpaceAptThreatDetectRule bo2Entity() {
		SpaceAptThreatDetectRule entity = BeanUtils.copy(this, SpaceAptThreatDetectRule.class);
		return entity;
	}

}
    
