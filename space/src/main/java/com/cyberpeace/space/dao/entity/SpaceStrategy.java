package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间策略
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceStrategy` 的 Java 类，它是用于表示“空间策略”的实体类。以下是对代码的简要分析： 
 * 1. **包与依赖**： 
 * - 该类位于 `com.cyberpeace.space.dao.entity` 包中。 
 * - 使用了 MyBatis-Plus 提供的注解（如 `@TableName` 和 `@TableId`）来映射数据库表和字段。 
 * - 继承了 `BaseTable` 类，可能是为了实现一些通用的数据库操作功能。 
 * 2. **注解说明**： 
 * - `@TableName("space_strategy")`：将该类映射到数据库中的 `space_strategy` 表。 
 * - `@TableId(type = IdType.ASSIGN_UUID)`：指定 `resourceId` 字段为主键，并使用 UUID 生成主键值。 
 * 3. **字段说明**： 
 * - `resourceId`：主键，类型为字符串，由 MyBatis-Plus 自动分配 UUID。 
 * - `name`、`userId`、`content`、`step`、`example`、`script`、`frameId`、`method`、`suggest`、`gauge`：这些字段分别存储不同的信息，具体含义需结合业务需求理解。 
 * - `view`：整数类型，可能用于表示某种视图或状态。 
 * - `scriptType`：整数类型，表示脚本类型。 
 * - `builtin`：布尔类型，可能用于标记是否为内置策略。 
 * 4. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：自动生成所有字段的 getter 和 setter 方法，简化了代码编写。 
 * 5. **继承关系**： 
 * - 继承自 `BaseTable`，可能是为了复用一些通用的数据库操作逻辑或字段。 
 * 总结：这是一个典型的 JPA 或 MyBatis 实体类，用于与数据库表 `space_strategy` 进行映射，支持基本的 CRUD 操作。 
**/
@Getter
@Setter
@TableName("space_strategy")
public class SpaceStrategy extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID)
	private String resourceId;

    private String name;

	private String userId;

	private Integer view;

	private String content;

	private String step;

	private String example;

	private String script;

	private Integer scriptType;

	private String frameId;

	private String method;

	private String suggest;

	private String gauge;

	private Boolean builtin;
}

