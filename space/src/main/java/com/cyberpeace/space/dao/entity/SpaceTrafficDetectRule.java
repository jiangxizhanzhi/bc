package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceTrafficDetectRuleBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 空间流量检测统治
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceTrafficDetectRule` 的 Java 实体类，用于表示空间流量检测规则。以下是代码的主要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`SpaceTrafficDetectRule` 
 * - 包名：`com.cyberpeace.space.dao.entity` 
 * - 注解 `@TableName("space_traffic_detect_rule")` 表明该类与数据库表 `space_traffic_detect_rule` 对应。 
 * 2. **继承关系**： 
 * - 该类继承自 `SpaceBaseTable`，表明它可能包含一些通用的字段或方法。 
 * 3. **字段说明**： 
 * - `ruleContent`：规则内容。 
 * - `ruleId`：规则编号。 
 * - `ruleInfo`：规则信息。 
 * - `threatLevel`：威胁等级。 
 * - `tool`：使用的工具。 
 * - `stage`：防御阶段。 
 * - `attackType`：攻击类型。 
 * 4. **方法说明**： 
 * - `entity2Bo(Map<String, IdAndName> relatedMap)`：将当前实体对象转换为 `SpaceTrafficDetectRuleBo` 对象。使用了 `BeanUtils.copy` 方法来实现属性的复制。 
 * 5. **注解说明**： 
 * - `@ApiModelProperty`：用于生成 API 文档时描述字段的含义。 
 * - `@Getter` 和 `@Setter`：自动生成字段的 getter 和 setter 方法。 
 * 6. **依赖的其他类**： 
 * - `com.cyberpeace.common.util.BeanUtils`：用于对象之间的属性复制。 
 * - `com.cyberpeace.space.model.IdAndName` 和 `com.cyberpeace.space.model.SpaceBaseTable`：分别是 `IdAndName` 和 `SpaceBaseTable` 类。 
 * - `com.cyberpeace.space.service.context.rule.SpaceTrafficDetectRuleBo`：目标 BO 类。 
 * 总结：这是一个典型的持久层实体类，主要用于数据库操作，并提供了从实体到业务对象的转换方法。 
**/
@Getter
@Setter
@TableName("space_traffic_detect_rule")
public class SpaceTrafficDetectRule extends SpaceBaseTable {

	@ApiModelProperty(value = "规则内容")
	private String ruleContent;

	@ApiModelProperty(value = "规则编号")
	private String ruleId;

	@ApiModelProperty(value = "规则信息")
	private String ruleInfo;

	@ApiModelProperty(value = "威胁等级")
	private String threatLevel;

	@ApiModelProperty(value = "工具")
	private String tool;

	@ApiModelProperty(value = "防御阶段， type=57")
	private Integer stage;

	@ApiModelProperty(value = "攻击类型， type=58")
	private Integer attackType;


	public SpaceTrafficDetectRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceTrafficDetectRuleBo bo = BeanUtils.copy(this, SpaceTrafficDetectRuleBo.class);
		return bo;
	}
}

