/**
 * 这段代码定义了一个名为 `SpaceEventIntegrationRule` 的 Java 实体类，用于表示空间事件集成规则。以下是对代码的简要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`SpaceEventIntegrationRule` 
 * - 包名：`com.cyberpeace.space.dao.entity` 
 * - 继承自 `SpaceBaseTable`，表明它是一个基于数据库表的实体类。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成 getter 和 setter 方法。 
 * 2. **注解说明**： 
 * - `@TableName("space_event_integration_rule")`：指定该类对应的数据库表名为 `space_event_integration_rule`。 
 * - `@ApiModelProperty`：用于生成 API 文档时描述字段的含义。 
 * 3. **字段说明**： 
 * - `type`：检测类型。 
 * - `description`：内容简介。 
 * - `content`：内容详情。 
 * - `tool`：应用工具。 
 * - `stage`：防御阶段，仅在 `type=57` 时有效。 
 * 4. **方法说明**： 
 * - `entity2Bo(Map<String, IdAndName> relatedMap)`：将当前实体对象转换为业务对象 `SpaceEventIntegrationRuleBo`，使用了 `BeanUtils.copy` 方法进行属性复制。 
 * 5. **依赖类**： 
 * - `SpaceBaseTable`：基类，可能包含一些通用字段或方法。 
 * - `IdAndName`、`SpaceEventIntegrationRuleBo`：与业务相关的模型类。 
 * - `BeanUtils`：工具类，用于对象之间的属性复制。 
 * 总结：该类是一个数据传输对象（DTO），主要用于存储和操作空间事件集成规则的数据，并提供了一种方式将其转换为业务对象。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceEventIntegrationRuleBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 空间事件集成规则
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_event_integration_rule")
public class SpaceEventIntegrationRule extends SpaceBaseTable {

	@ApiModelProperty(value = "检测类型")
	private String type;

	@ApiModelProperty(value = "内容简介")
	private String description;

	@ApiModelProperty(value = "内容详情")
	private String content;

	@ApiModelProperty(value = "应用工具")
	private String tool;

	@ApiModelProperty(value = "防御阶段， type=57")
	private Integer stage;

	public SpaceEventIntegrationRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceEventIntegrationRuleBo bo = BeanUtils.copy(this, SpaceEventIntegrationRuleBo.class);
		return bo;
	}
}
