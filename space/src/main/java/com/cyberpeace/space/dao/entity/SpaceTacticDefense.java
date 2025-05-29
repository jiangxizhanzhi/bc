package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间战法防御
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceTacticAttack
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceTacticDefense` 的 Java 类，它继承自 `SpaceTacticAttack`。以下是代码的简要分析： 
 * 1. **类的定义**： 
 * - `SpaceTacticDefense` 是一个实体类，用于表示空间战法防御的相关信息。 
 * - 它使用了 Lombok 注解 `@Getter` 和 `@Setter`，自动生成 getter 和 setter 方法。 
 * 2. **表注解**： 
 * - 使用了 `@TableName("space_tactic_defense")` 注解，表明该类映射到数据库中的 `space_tactic_defense` 表。 
 * 3. **主键定义**： 
 * - 使用了 MyBatis-Plus 的 `@TableId` 注解来定义主键字段 `resourceId`，并指定其类型为 `IdType.ASSIGN_ID`，表示主键由框架自动生成。 
 * 4. **继承关系**： 
 * - 该类继承自 `SpaceTacticAttack`，可能包含一些通用的属性或方法。 
 * 总体而言，这个类是一个数据访问对象（DAO）实体类，主要用于操作数据库中 `space_tactic_defense` 表的数据。 
**/
@Getter
@Setter
@TableName("space_tactic_defense")
public class SpaceTacticDefense extends SpaceTacticAttack {

	@TableId(type = IdType.ASSIGN_ID)
	private String resourceId;

}

