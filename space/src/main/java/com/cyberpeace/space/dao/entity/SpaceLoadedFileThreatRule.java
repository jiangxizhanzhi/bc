package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceLoadedFileThreatRuleBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 空间加载文件威胁规则
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 1. **类的基本信息** 
 * - **类名**: `SpaceLoadedFileThreatRule` 
 * - **包名**: `com.cyberpeace.space.dao.entity` 
 * - **功能描述**: 这是一个实体类，用于表示“空间加载文件威胁规则”的数据模型。它继承自`SpaceBaseTable`，并使用了MyBatis-Plus的注解`@TableName`来映射数据库表`space_loaded_file_threat_rule`。 
 * #### 2. **继承与依赖** 
 * - **继承**: 
 * - 继承自`SpaceBaseTable`，表明该类可能包含一些通用的基础字段（如ID、创建时间、更新时间等）。 
 * - **依赖**: 
 * - 使用了`BeanUtils.copy`方法进行对象转换。 
 * - 引用了`IdAndName`、`SpaceBaseTable`、`SpaceLoadedFileThreatRuleBo`等类或接口。 
 * - 使用了Lombok的`@Getter`和`@Setter`注解，简化了属性的getter和setter生成。 
 * #### 3. **字段说明** 
 * - **`type`**: 检测类型，通过`@ApiModelProperty`标注，表示API文档中的字段描述。 
 * - **`description`**: 内容简介。 
 * - **`content`**: 内容详情。 
 * - **`tool`**: 应用工具。 
 * - **`executeType`**: 执行方式，值为1表示手动，值为2表示自动。 
 * - **`stage`**: 阶段。 
 * #### 4. **方法说明** 
 * - **`entity2Bo`**: 将当前实体对象转换为`SpaceLoadedFileThreatRuleBo`对象。 
 * - 参数: `relatedMap`，一个`Map<String, IdAndName>`类型的参数，可能是为了处理某些关联数据。 
 * - 功能: 使用`BeanUtils.copy`方法将当前对象的字段值复制到目标对象中，并返回结果。 
 * #### 5. **注解说明** 
 * - **`@TableName`**: 表示该类对应的数据库表名为`space_loaded_file_threat_rule`。 
 * - **`@ApiModelProperty`**: 用于生成API文档时描述字段的含义。 
 * #### 6. **总结** 
 * - 该类是一个典型的实体类，用于数据库表的映射。 
 * - 提供了对象转换的功能，便于在不同层之间传递数据。 
 * - 使用了Lombok简化代码，提升了可读性和开发效率。 
**/
@Getter
@Setter
@TableName("space_loaded_file_threat_rule")
public class SpaceLoadedFileThreatRule extends SpaceBaseTable {

	@ApiModelProperty(value = "检测类型")
	private String type;

	@ApiModelProperty(value = "内容简介")
	private String description;

	@ApiModelProperty(value = "内容详情")
	private String content;

	@ApiModelProperty(value = "应用工具")
	private String tool;

	@ApiModelProperty(value = "执行方式，自动2，手动1")
	private Integer executeType;

	private Integer stage;


	public SpaceLoadedFileThreatRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceLoadedFileThreatRuleBo bo = BeanUtils.copy(this, SpaceLoadedFileThreatRuleBo.class);
		return bo;
	}
}

