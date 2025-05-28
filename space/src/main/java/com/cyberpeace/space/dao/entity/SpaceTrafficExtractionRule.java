/**
 * ### 代码分析 
 * #### 类的功能 
 * - **类名**：`SpaceTrafficExtractionRule` 
 * - **功能描述**：该类是用于表示“空间流量萃取规则”的实体类，继承自`SpaceBaseTable`。它通过MyBatis-Plus注解`@TableName`映射到数据库表`space_traffic_extraction_rule`。 
 * --- 
 * #### 关键点解析 
 * 1. **注解说明** 
 * - `@TableName("space_traffic_extraction_rule")`：将类映射为数据库中的表`space_traffic_extraction_rule`。 
 * - `@TableField("`order`")`：由于`order`是SQL保留字，使用反引号（``）包裹以避免冲突。 
 * - `@Getter` 和 `@Setter`：Lombok注解，自动生成getter和setter方法。 
 * 2. **字段说明** 
 * - `order`：表示规则的顺序，用字符串类型存储，由`@ApiModelProperty`注释描述其含义。 
 * - `content` 和 `tool`：具体规则内容及关联工具字段，未提供详细注释，推测用于存储规则相关信息。 
 * 3. **方法** 
 * - `entity2Bo(Map<String, IdAndName> relatedMap)`：将当前实体对象转换为业务对象`SpaceTrafficExtractionRuleBo`。通过`BeanUtils.copy`实现字段复制。 
 * 4. **依赖类** 
 * - `SpaceBaseTable`：父类，可能包含通用字段或方法。 
 * - `SpaceTrafficExtractionRuleBo`：目标业务对象类。 
 * - `IdAndName`：可能是一个简单的数据传输对象（DTO），用于存储ID和名称。 
 * - `BeanUtils`：工具类，可能提供了对象之间的字段拷贝功能。 
 * --- 
 * #### 总结 
 * - **主要用途**：该类作为数据持久化层的实体类，负责与数据库交互，并提供业务对象转换功能。 
 * - **设计特点**： 
 * - 使用了Lombok简化代码，减少冗余。 
 * - 数据库字段与Java字段一一对应，便于ORM框架操作。 
 * - 提供了实用的方法，方便从实体类生成业务对象。 
 * - **扩展性**：如果需要新增字段或功能，只需在类中添加对应的属性和方法即可，符合面向对象的设计原则。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceTrafficExtractionRuleBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 空间流量萃取统治
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_traffic_extraction_rule")
public class SpaceTrafficExtractionRule extends SpaceBaseTable {

	@ApiModelProperty(value = "顺序")
	@TableField("`order`")
	private String order;

	// @ApiModelProperty(value = "文件名")
	// private String filename;
	//
	// @ApiModelProperty(value = "路径")
	// private String route;
	private String content;

	private String tool;



	public SpaceTrafficExtractionRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceTrafficExtractionRuleBo bo = BeanUtils.copy(this, SpaceTrafficExtractionRuleBo.class);
		return bo;
	}
}
