package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间行动框架下发
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseTable
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `SpaceActionFrameRelease` 的实体类，用于表示与数据库表 `space_action_frame_release` 对应的数据模型。以下是代码的主要分析： 
 * 1. **继承关系**： 
 * - 类 `SpaceActionFrameRelease` 继承自 `BaseTable`，表明它是一个基于 MyBatis-Plus 的数据表映射实体。 
 * 2. **注解说明**： 
 * - `@TableName("space_action_frame_release")`：指定该实体类对应的数据库表名为 `space_action_frame_release`。 
 * - `@TableId(type = IdType.AUTO)`：声明字段 `id` 是主键，并且其生成策略为自动（通常对应数据库中的自增）。 
 * 3. **字段说明**： 
 * - `id`：主键字段，类型为整数。 
 * - `applicationId`、`rvbId`、`frameName`、`step`、`userId`、`userName`、`tactics`：这些字段分别代表不同的业务信息，如应用ID、RBV ID、框架名称、步骤、用户ID、用户名和策略等。 
 * 4. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：自动生成所有字段的getter和setter方法，简化了代码量。 
 * 5. **用途**： 
 * - 这个类主要用于操作数据库中 `space_action_frame_release` 表的数据，支持增删改查等功能。 
 * 总结来说，这是一个典型的 MyBatis-Plus 数据库实体类，设计用于管理特定业务场景下的空间行动框架下发相关数据。 
**/
@Getter
@Setter
@TableName("space_action_frame_release")
public class SpaceActionFrameRelease extends BaseTable {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private String applicationId;

	private String rvbId;

	private String frameName;

	private String step;

	private String userId;

	private String userName;

	private String tactics;
}

