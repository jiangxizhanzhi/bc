package com.cyberpeace.space.service.context.rule;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceViolationJudgeRule;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间违反法官统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceViolationJudgeRuleBo` 的 Java 类，继承自 `SpaceBoBase`。以下是对代码的简要分析： 
 * 1. **类的功能**： 
 * - `SpaceViolationJudgeRuleBo` 是一个业务对象（BO），用于表示空间违反法官规则的相关信息。 
 * - 它包含了多个字段来描述规则的属性，例如资源 ID、名称、间隔时间、端口检测和目标检测等。 
 * 2. **字段说明**： 
 * - `resourceId`：资源 ID，用于唯一标识某个资源。 
 * - `name`：规则的名称。 
 * - `interval`：提交答案的间隔时间，单位为秒。 
 * - `portCheck`：是否进行端口检测。 
 * - `targetCheck`：是否进行攻击目标检测。 
 * 3. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为 API 文档提供字段描述。 
 * - 使用了 MyBatis-Plus 的 `@TableField` 注解，指定数据库表中字段名可能需要特殊处理（如关键字冲突）。 
 * 4. **方法**： 
 * - `bo2Entity()`：将当前 BO 对象转换为对应的实体对象 `SpaceViolationJudgeRule`。通过 `BeanUtils.copy` 方法实现浅拷贝。 
 * 5. **继承关系**： 
 * - 继承自 `SpaceBoBase`，可能包含一些通用的基础功能或默认实现。 
 * 6. **依赖**： 
 * - 使用了外部工具类 `BeanUtils` 进行对象间的属性复制。 
 * - 依赖于 `SpaceViolationJudgeRule` 实体类，用于数据持久化。 
 * 总结：该类是一个典型的业务对象类，主要用于封装空间违反法官规则的数据，并提供了从 BO 到实体的转换方法，便于在业务逻辑和数据存储之间传递数据。 
**/

@Getter
@Setter
public class SpaceViolationJudgeRuleBo extends SpaceBoBase {

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
	 * 间隔
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "提交答案间隔")
	@TableField("`interval`")
	private Integer interval;

	/**
	 * 端口检查
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "端口检测")
	private Boolean portCheck;

	/**
	 * 目标检查
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "攻击目标检测")
	private Boolean targetCheck;

	/**
	 * BO2 实体
	 *
	 * @return 空间违规裁判规则
	 * @throws
	 * @see SpaceViolationJudgeRule
	 */
	public SpaceViolationJudgeRule bo2Entity() {
		SpaceViolationJudgeRule entity = BeanUtils.copy(this, SpaceViolationJudgeRule.class);
		return entity;
	}

}
    
