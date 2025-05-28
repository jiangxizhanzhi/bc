/**
 * 该代码定义了一个名为 `SpaceTacticAttack` 的实体类，用于表示空间战法攻击的相关信息。以下是代码的简要分析： 
 * 1. **继承与注解**： 
 * - `SpaceTacticAttack` 类继承自 `BaseTable`，表明它是一个数据表的实体类，并可能包含一些通用的字段或方法。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的getter和setter方法。 
 * 2. **MyBatis-Plus 支持**： 
 * - 使用了 `@TableName` 注解，指定该类对应的数据库表名为 `space_tactic_attack`。 
 * - 使用了 `@TableId` 注解，指定 `resourceId` 字段为主键，并使用 `IdType.ASSIGN_ID` 表示主键类型为自动分配的UUID。 
 * 3. **字段说明**： 
 * - `resourceId`：主键字段，类型为字符串。 
 * - `name`、`categoryId`、`source`、`systemId`、`action`、`tool`、`strategy`：分别表示名称、类别ID、来源、系统ID、操作、工具、策略等信息。 
 * - `builtin`：布尔值，表示是否为内置项。 
 * 4. **用途**： 
 * - 该类主要用于持久化空间战法攻击的相关信息到数据库中，支持 MyBatis-Plus 的操作。 
 * 总结：这是一个典型的 MyBatis-Plus 实体类，用于映射数据库表 `space_tactic_attack`，并提供了基本的 CRUD 操作支持。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间战法攻击
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_tactic_attack")
public class SpaceTacticAttack extends BaseTable {

	@TableId(type = IdType.ASSIGN_ID)
	private String resourceId;

	private String name;

	private String categoryId;

	private String source;

	private String systemId;

	private String action;

	private String tool;

	private String strategy;

	private Boolean builtin;
}
