/**
 * 这段代码定义了一个名为 `SpaceTacticAttackCategory` 的 Java 类，用于表示数据库中的 "space_tactic_attack_category" 表。以下是代码的主要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`SpaceTacticAttackCategory` 
 * - 包名：`com.cyberpeace.space.dao.entity` 
 * - 功能：表示空间战法攻击类别的实体类。 
 * 2. **继承与注解**： 
 * - 继承自 `BaseTable`，表明该类可能包含一些通用的数据库表字段或方法。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * 3. **数据库表映射**： 
 * - 使用了 MyBatis-Plus 的 `@TableName` 注解，将类映射到数据库中的 "space_tactic_attack_category" 表。 
 * 4. **主键定义**： 
 * - 使用了 MyBatis-Plus 的 `@TableId` 注解，定义了主键字段 `resourceId`，并指定其生成策略为 `IdType.ASSIGN_ID`（通常对应雪花算法生成的 ID）。 
 * 5. **字段说明**： 
 * - `resourceId`：主键字段，类型为 `String`。 
 * - `name`：表示攻击类别的名称，类型为 `String`。 
 * 6. **注释**： 
 * - 类上有详细的注释，描述了类的功能、作者、版本和依赖的基类 `BaseTable`。 
 * 总结：这是一个典型的 MyBatis-Plus 实体类，用于与数据库表进行映射，支持自动化的 CRUD 操作。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间战法攻击类别
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_tactic_attack_category")
public class SpaceTacticAttackCategory extends BaseTable {

	@TableId(type = IdType.ASSIGN_ID)
	private String resourceId;

	private String name;

}
