/**
 * 这段代码定义了一个名为 `CrMark` 的 Java 实体类，用于表示数据库表 `cr_mark` 中的数据。以下是代码的主要分析： 
 * 1. **类与注解**： 
 * - 类 `CrMark` 继承自 `BaseTable`，这是一个基类，可能包含通用的字段或方法。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter`，自动生成属性的 getter 和 setter 方法。 
 * - `@TableName` 注解指定了该类对应的数据库表名为 `cr_mark`。 
 * 2. **主键定义**： 
 * - 使用了 MyBatis-Plus 的 `@TableId` 注解来定义主键 `resource_id`，其类型为 UUID，并且在数据库中对应的字段名为 `resource_id`。 
 * 3. **字段说明**： 
 * - `resourceId`：资源 ID，作为主键。 
 * - `name`：名称。 
 * - `description`：描述信息。 
 * - `evidence`：证据。 
 * - `userId`：用户 ID。 
 * - `roomId`：房间 ID。 
 * - `type`：训练类型，整数类型。 
 * 4. **用途**： 
 * - 该类主要用于数据持久化，通过 MyBatis-Plus 框架与数据库进行交互。 
 * 总结：`CrMark` 是一个典型的实体类，结合了 Lombok 和 MyBatis-Plus 的特性，用于操作数据库表 `cr_mark` 中的数据。 
**/
package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * cr标记
 *
 * @author SN
 * @date 2023/05/24
 */
@TableName(value = "cr_mark")
@Getter
@Setter
public class CrMark extends BaseTable {

    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 证据
     */
    private String evidence;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 训练类型
     */
    private Integer type;
}
