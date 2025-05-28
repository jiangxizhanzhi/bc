/**
 * 这段代码定义了一个名为 `CourseCategory` 的 Java 类，用于表示课程类别。以下是对该代码的简要分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.oj.course.dao.entity` 
 * - **导入**： 
 * - `com.baomidou.mybatisplus.annotation.IdType` 和 `com.baomidou.mybatisplus.annotation.TableId`：用于 MyBatis-Plus 框架中的主键生成策略。 
 * - `com.baomidou.mybatisplus.annotation.TableName`：用于指定数据库表名。 
 * - `com.cyberpeace.common.baseinterface.GenericEnum`：一个通用枚举接口。 
 * - `com.cyberpeace.common.model.BaseTable`：一个基类，可能包含一些公共字段或方法。 
 * - `lombok.*`：Lombok 注解库，简化了 Java 对象的创建。 
 * ### 类定义 
 * - **类名**：`CourseCategory` 
 * - **继承**：`BaseTable` 
 * - **注解**： 
 * - `@Getter`、`@Setter`：自动生成 getter 和 setter 方法。 
 * - `@Builder`：使用 Lombok 自动生成构建器模式。 
 * - `@AllArgsConstructor`、`@NoArgsConstructor`：自动生成全参数构造函数和无参数构造函数。 
 * ### 类属性 
 * - `resource_id`：资源 ID，使用 UUID 作为主键。 
 * - `name`：名称。 
 * - `courseType`：课程类型，使用枚举 `CourseType`。 
 * - `code`：代号。 
 * - `parentId`：父 ID。 
 * - `builtin`：布尔值，表示是否为内置项。 
 * ### 枚举定义 
 * - **枚举名**：`CourseType` 
 * - **实现**：`GenericEnum` 
 * - **枚举值**： 
 * - `BASE`：基础课程，代码为 0。 
 * - `ATTACK`：攻击课程，代码为 2。 
 * - `DEFENSE`：防御课程，代码为 3。 
 * ### 总结 
 * - **功能**：`CourseCategory` 类用于表示课程类别，包括资源 ID、名称、课程类型、代号、父 ID 和是否为内置项等信息。 
 * - **技术栈**：使用了 MyBatis-Plus 进行数据库操作，Lombok 库简化了代码编写。 
 * - **扩展性**：通过枚举 `CourseType` 来表示课程类型，易于扩展新的课程类型。 
 * 这段代码简洁明了，利用了 Lombok 和 MyBatis-Plus 提供的功能，提高了开发效率。 
**/
package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.*;

/**
 * 课程类别
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oj_course_category")
public class CourseCategory extends BaseTable {

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
	 * 课程类型
	 *
	 * @see Integer
	 */
	private Integer courseType;

	/**
	 * 代号
	 *
	 * @see String
	 */
	private String code;

	/**
	 * 父母id
	 *
	 * @see String
	 */
	private String parentId;

	/**
	 * 内置
	 *
	 * @see Boolean
	 */
	private Boolean builtin;

	public enum CourseType implements GenericEnum {

		BASE(0, "基础"), ATTACK(2, "攻击"), DEFENSE(3, "防御");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private CourseType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
