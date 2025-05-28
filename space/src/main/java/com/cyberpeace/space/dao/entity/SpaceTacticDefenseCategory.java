/**
 * 这段代码定义了一个名为 `SpaceTacticDefenseCategory` 的 Java 类，它是一个实体类，用于表示与数据库表 `space_tactic_defense_category` 对应的数据模型。以下是对其的简要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`SpaceTacticDefenseCategory` 
 * - 包名：`com.cyberpeace.space.dao.entity` 
 * - 作者：`SN` 
 * - 版本：`1.0.0` 
 * - 创建日期：`2025/05/28` 
 * 2. **继承与注解**： 
 * - 该类继承自 `BaseTable`，这是一个通用的基类，通常用于提供一些基础功能或属性。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成类中所有字段的 getter 和 setter 方法。 
 * 3. **MyBatis-Plus 支持**： 
 * - 使用了 `@TableName` 注解，将该类映射到数据库中的表 `space_tactic_defense_category`。 
 * - 使用了 `@TableId` 注解，指定 `resourceId` 字段作为主键，并使用 `IdType.ASSIGN_UUID` 类型，表示主键值由 MyBatis-Plus 自动分配 UUID。 
 * 4. **字段说明**： 
 * - `resourceId`：主键，类型为字符串，使用 UUID 作为唯一标识。 
 * - `name`：名称，类型为字符串。 
 * - `stage`：阶段，类型为整数。 
 * - `cover`：封面，类型为字符串。 
 * 5. **用途**： 
 * - 该类主要用于表示空间战法防御类别的数据实体，适用于数据库操作和业务逻辑处理。 
 * 总结：这是一个典型的基于 MyBatis-Plus 的实体类，用于数据库表的映射和操作，提供了基本的 CRUD 功能支持。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间战法防御类别
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_tactic_defense_category")
public class SpaceTacticDefenseCategory extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID)
	private String resourceId;

	private String name;

	private Integer stage;

	private String cover;

}
