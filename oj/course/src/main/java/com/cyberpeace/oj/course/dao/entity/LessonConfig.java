package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.*;


/**
 * 课程配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonConfig` 的实体类，用于表示课程配置信息。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.dao.entity` 
 * - **继承关系**：该类继承自 `BaseTable`（假设这是一个基类，可能包含一些通用的字段或方法） 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：为所有字段生成 getter 和 setter 方法。 
 * - `@Builder`：允许使用构建器模式来创建对象。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：分别生成所有参数的构造函数和无参构造函数。 
 * ### 表定义 
 * - **表名**：`oj_lesson_config` 
 * - **主键**： 
 * - `resource_id` 字段作为主键，使用 UUID 类型。 
 * ### 字段 
 * 1. **resource_id** (String)：资源ID，用作主键。 
 * 2. **courseId** (String)：课程ID。 
 * 3. **lessonId** (String)：课时ID。 
 * 4. **index** (Integer)：索引值。 
 * 5. **parentId** (String)：父节点ID。 
 * 6. **name** (String)：名称。 
 * 7. **nodeType** (Integer)：节点类型。 
 * ### 枚举类型 
 * - **NodeType**：定义了节点类型的枚举，实现 `GenericEnum` 接口。 
 * - **枚举值**： 
 * - `NODE_COURSE`：课程，代码为 0，描述为 "课程"。 
 * - `NODE_CHAPTER`：章节，代码为 1，描述为 "章节"。 
 * - `NODE_LESSON`：课时，代码为 2，描述为 "课时"。 
 * ### 总结 
 * - **功能**：`LessonConfig` 类用于存储课程配置信息，包括资源ID、课程ID、课时ID、索引值、父节点ID、名称和节点类型。 
 * - **特性**：使用 Lombok 注解简化了代码，方便生成 getter 和 setter 方法以及构造函数。 
 * - **枚举**：定义了节点类型的枚举，便于管理和维护节点类型。 
 * 这个类主要用于数据库表 `oj_lesson_config` 的映射，可以用于持久化课程配置数据。 
**/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oj_lesson_config")
public class LessonConfig extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	private String courseId;

	/**
	 * 课id
	 *
	 * @see String
	 */
	private String lessonId;

	/**
	 * 指数
	 *
	 * @see Integer
	 */
	@TableField("`index`")
	private Integer index;

	/**
	 * 父母id
	 *
	 * @see String
	 */
	private String parentId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 节点类型
	 *
	 * @see Integer
	 */
	private Integer nodeType;

	public enum NodeType implements GenericEnum {

		NODE_COURSE(0, "课程"),

		NODE_CHAPTER(1, "章节"),

		NODE_LESSON(2, "课时");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		NodeType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}

