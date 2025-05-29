package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间策略框架
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceStrategyFrame` 的实体类，用于表示“空间策略框架”的数据模型。以下是简要分析： 
 * 1. **继承与注解**： 
 * - 类 `SpaceStrategyFrame` 继承自 `BaseTable`，表明它是一个基于数据库表的实体类，并可能包含一些通用字段（如创建时间、更新时间等）。 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * 2. **MyBatis-Plus 支持**： 
 * - 使用了 `@TableName` 注解，指定该实体类对应数据库中的表名为 `space_strategy_frame`。 
 * - 使用了 `@TableId` 注解，指定主键字段为 `resourceId`，并且主键类型为 `ASSIGN_UUID`，表示主键值由 MyBatis-Plus 自动分配 UUID。 
 * 3. **字段说明**： 
 * - `resourceId`：主键字段，类型为字符串。 
 * - `name`：策略框架的名称，类型为字符串。 
 * - `parent`：父级策略框架的标识，类型为字符串。 
 * - `builtin`：布尔值，表示该策略框架是否为内置的。 
 * 4. **设计目的**： 
 * - 该类主要用于描述空间策略框架的数据结构，并通过 MyBatis-Plus 实现与数据库的交互。 
 * 总结：这是一个典型的 MyBatis-Plus 实体类，结合了 Lombok 和自定义注解，用于管理数据库中的 `space_strategy_frame` 表数据。 
**/
@Getter
@Setter
@TableName("space_strategy_frame")
public class SpaceStrategyFrame extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID)
	private String resourceId;

	private String name;

	private String parent;

	private Boolean builtin;

}

