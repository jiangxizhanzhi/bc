/**
 * 这段代码定义了一个名为 `SpaceActionRelation` 的实体类，用于表示数据库表 `space_action_relation` 中的数据。以下是代码的详细分析： 
 * ### 1. **类的基本信息** 
 * - **包名**：`com.cyberpeace.space.dao.entity` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 提供，自动生成类中所有字段的 getter 和 setter 方法。 
 * - `@TableName("space_action_relation")`：MyBatis-Plus 注解，指定该类对应的数据库表名为 `space_action_relation`。 
 * ### 2. **字段说明** 
 * - **`id`**： 
 * - 类型：`Integer` 
 * - 注解： 
 * - `@TableId(type = IdType.AUTO)`：MyBatis-Plus 注解，表示该字段为主键，并且主键值由数据库自动生成（自动递增）。 
 * - 功能：作为表中的唯一标识符。 
 * - **`upperId`**： 
 * - 类型：`String` 
 * - 功能：表示某种“上级”ID，具体含义需要结合业务逻辑理解。 
 * - **`lowerId`**： 
 * - 类型：`String` 
 * - 功能：表示某种“下级”ID，与 `upperId` 对应，可能用于描述父子关系或其他关联关系。 
 * - **`createTime`**： 
 * - 类型：`Date` 
 * - 注解： 
 * - `@TableField(fill = FieldFill.INSERT)`：MyBatis-Plus 注解，表示该字段在插入数据时会自动填充当前时间。 
 * - 功能：记录数据创建的时间戳。 
 * ### 3. **代码总结** 
 * - **用途**：该类是 MyBatis-Plus 的实体类，用于映射数据库表 `space_action_relation`。 
 * - **特点**： 
 * - 使用了 Lombok 和 MyBatis-Plus 的注解，减少了冗余代码。 
 * - 主键 `id` 自动递增，`createTime` 自动填充插入时间。 
 * - 字段 `upperId` 和 `lowerId` 可能用于描述某种层次关系或关联关系。 
 * ### 4. **适用场景** 
 * 该类适用于需要管理空间或对象之间层级关系的场景，例如组织架构、树形结构等。通过 `upperId` 和 `lowerId`，可以灵活地表示上下级关系。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 空间作关系
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_action_relation")
public class SpaceActionRelation {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private String upperId;

	private String lowerId;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
}
