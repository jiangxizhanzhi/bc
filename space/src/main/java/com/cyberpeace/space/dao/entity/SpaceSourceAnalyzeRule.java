package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceSourceAnalyzeRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间源分析规则
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 类概述 
 * - **类名**: `SpaceSourceAnalyzeRule` 
 * - **包名**: `com.cyberpeace.space.dao.entity` 
 * - **功能**: 该类表示空间源分析规则的实体类，继承自`SpaceBaseTable`，并使用了 MyBatis-Plus 的注解`@TableName`来映射数据库表。 
 * #### 主要特性 
 * 1. **继承与注解**: 
 * - 继承自`SpaceBaseTable`，可能是为了复用一些基础字段或方法。 
 * - 使用`@TableName("space_source_analyze_rule")`注解，将该类映射到数据库中的`space_source_analyze_rule`表。 
 * 2. **字段**: 
 * - `rule`: 表示规则列表，类型为`String`，通过`@ApiModelProperty`注解描述其含义。 
 * 3. **方法**: 
 * - `entity2Bo(Map<String, IdAndName> relatedMap)`: 将当前实体对象转换为`SpaceSourceAnalyzeRuleBo`对象。使用了工具类`BeanUtils.copy`进行对象复制。 
 * 4. **Lombok 注解**: 
 * - `@Getter` 和 `@Setter`: 自动生成字段的getter和setter方法。 
 * #### 总结 
 * - 该类是一个数据访问层（DAO）中的实体类，用于表示空间源分析规则的数据结构。 
 * - 提供了从实体类到业务对象（BO）的转换方法，便于在不同层次之间传递数据。 
 * - 使用了 Lombok 和 MyBatis-Plus 等框架简化代码编写。 
**/
@Getter
@Setter
@TableName("space_source_analyze_rule")
public class SpaceSourceAnalyzeRule extends SpaceBaseTable {

	@ApiModelProperty(value = "规则列表")
	private String rule;


	public SpaceSourceAnalyzeRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceSourceAnalyzeRuleBo bo = BeanUtils.copy(this, SpaceSourceAnalyzeRuleBo.class);
		return bo;
	}
}

