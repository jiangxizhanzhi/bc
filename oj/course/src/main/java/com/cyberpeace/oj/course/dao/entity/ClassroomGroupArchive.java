/**
 * 这段代码定义了一个名为 `ClassroomGroupArchive` 的实体类，用于表示课堂组的存档信息。以下是对其主要特性的分析： 
 * 1. **包声明与导入**: 
 * - 该类位于 `com.cyberpeace.oj.course.dao.entity` 包下。 
 * - 导入了多个依赖，包括 MyBatis-Plus 注解 (`@TableName`, `@TableId`) 和 Lombok 注解 (`@Getter`, `@Setter`)。 
 * 2. **继承关系**: 
 * - `ClassroomGroupArchive` 类继承自 `BaseTable`，表明它是一个持久化实体类，通常会包含一些通用的数据库表字段（如创建时间、更新时间等）。 
 * 3. **注解**: 
 * - `@TableName("oj_classroom_group_archive")`: 指定该类对应的数据库表名。 
 * - `@TableId(type = IdType.UUID, value = "resource_id")`: 定义 `resource_id` 字段作为主键，并且使用 UUID 类型生成主键值。 
 * 4. **属性**: 
 * - `private String resourceId;`: 存储资源ID，用作主键。 
 * - `private String name;`: 存储名称。 
 * - `private String groups;`: 存储组信息。 
 * - `private String classroomId;`: 存储课堂ID。 
 * 5. **Lombok 注解**: 
 * - `@Getter`: 为所有属性生成 getter 方法。 
 * - `@Setter`: 为所有属性生成 setter 方法。 
 * 6. **文档注释**: 
 * - 类和属性都有详细的 Javadoc 注释，描述了它们的功能和用途。 
 * 总体来说，`ClassroomGroupArchive` 是一个数据传输对象 (DTO)，用于存储和操作课堂组存档的信息。通过继承 `BaseTable`，它具备了一些基础的 CRUD 功能，而 MyBatis-Plus 注解则帮助其与数据库进行映射。 
**/
package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * 课堂组存档
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_classroom_group_archive")
public class ClassroomGroupArchive extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 组
	 *
	 * @see String
	 */
	private String groups;

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	private String classroomId;

}
