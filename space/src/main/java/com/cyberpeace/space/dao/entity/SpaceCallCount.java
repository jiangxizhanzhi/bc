package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 调用计数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/12
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceCallCount` 的实体类，用于表示数据库中的 "space_call_count" 表。以下是代码的主要分析： 
 * 1. **包与导入**： 
 * - 类位于 `com.cyberpeace.space.dao.entity` 包中。 
 * - 使用了 MyBatis-Plus 提供的注解 (`@TableName`, `@TableId`) 和 Lombok 注解 (`@Getter`, `@Setter`)。 
 * 2. **注解说明**： 
 * - `@TableName("space_call_count")`：指定该类对应的数据库表名为 `space_call_count`。 
 * - `@TableId(type = IdType.AUTO)`：定义 `id` 字段为主键，并使用自增策略（`IdType.AUTO`）。 
 * - `@Getter` 和 `@Setter`：通过 Lombok 自动生成字段的 getter 和 setter 方法。 
 * 3. **字段说明**： 
 * - `id`：主键字段，类型为 `Integer`，用于唯一标识记录。 
 * - `name`：存储名称的字符串字段。 
 * - `calls`：记录调用次数的整型字段。 
 * 4. **用途**： 
 * - 该类主要用于数据持久化，与数据库中的 `space_call_count` 表进行映射。 
 * - 提供了基本的 CRUD 操作支持，便于操作数据库表中的记录。 
 * 总结：这是一个简单的实体类，结合了 MyBatis-Plus 和 Lombok，用于管理 `space_call_count` 表的数据。 
**/
@Getter
@Setter
@TableName("space_call_count")
public class SpaceCallCount {

    /**
     * id
     */
	@TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 调用
     */
    private Integer calls;

}

