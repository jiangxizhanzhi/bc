package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceViolationJudgeRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间违规裁判规则
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceViolationJudgeRule` 的实体类，用于表示空间违规裁判规则。以下是代码的主要分析和总结： 
 * 1. **类的继承与注解**： 
 * - `SpaceViolationJudgeRule` 继承自 `SpaceBaseTable`，表明它是一个基于数据库表的实体类。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，简化了属性的 getter 和 setter 方法生成。 
 * - `@TableName("space_violation_judge_rule")` 注解指定了对应的数据库表名。 
 * 2. **字段说明**： 
 * - `interval`：表示提交答案的间隔时间，使用 `@TableField("`interval`")` 映射到数据库中的字段。 
 * - `portCheck` 和 `targetCheck`：分别表示端口检测和攻击目标检测的布尔值。 
 * 3. **方法说明**： 
 * - `entity2Bo` 方法将当前实体对象转换为 `SpaceViolationJudgeRuleBo` 对象。通过 `BeanUtils.copy` 方法实现属性的复制。 
 * 4. **依赖的其他类**： 
 * - 使用了 `com.cyberpeace.common.util.BeanUtils` 进行对象属性的复制。 
 * - 引入了 `IdAndName` 和 `SpaceViolationJudgeRuleBo` 模型类，以及 `SpaceBaseTable` 基础类。 
 * 5. **注释和文档**： 
 * - 类和字段都有详细的注释，描述了每个字段的功能和用途。 
 * 总结来说，这个类主要用于表示空间违规裁判规则的数据结构，并提供了一种将实体对象转换为业务对象的方法。 
**/
@Getter
@Setter
@TableName("space_violation_judge_rule")
public class SpaceViolationJudgeRule extends SpaceBaseTable {

	@ApiModelProperty(value = "提交答案间隔")
	@TableField("`interval`")
	private Integer interval;

	@ApiModelProperty(value = "端口检测")
	private Boolean portCheck;

	@ApiModelProperty(value = "攻击目标检测")
	private Boolean targetCheck;


	public SpaceViolationJudgeRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceViolationJudgeRuleBo bo = BeanUtils.copy(this, SpaceViolationJudgeRuleBo.class);
		return bo;
	}
}

