/**
 * 这段代码定义了一个名为 `LessonRecord` 的实体类，用于表示课程记录。以下是对该代码的简要分析： 
 * ### 包与导入 
 * - **包声明**：`com.cyberpeace.oj.course.dao.entity` 
 * - **导入的库**： 
 * - MyBatis Plus 相关注解：`@TableId`, `@TableName` 
 * - 自定义接口：`GenericEnum`, `BaseTable` 
 * - Lombok 注解：`@Getter`, `@Setter`, `@Builder`, `@AllArgsConstructor`, `@NoArgsConstructor` 
 * ### 类定义 
 * - **继承**：`LessonRecord` 继承自 `BaseTable` 
 * - **注解**： 
 * - `@TableName("oj_lesson_record")`：指定数据库表名 
 * - `@Getter`, `@Setter`: 自动生成 getter 和 setter 方法 
 * - `@Builder`: 自动生成构建器模式的方法 
 * - `@AllArgsConstructor`, `@NoArgsConstructor`: 自动生成全参构造函数和无参构造函数 
 * ### 属性 
 * - `resourceId`：资源ID，使用 `@TableId(type = IdType.UUID)` 标识为 UUID 类型的主键 
 * - `lessonId`：课ID 
 * - `userId`：用户ID 
 * - `courseId`：课程ID 
 * - `roomId`：房间ID 
 * - `learnedType`：学习类型，整数类型 
 * ### 枚举类型 
 * - `LearnedType`：枚举类型，实现了 `GenericEnum` 接口，包含两个枚举值： 
 * - `COURSE`：值为 0，描述为“课程” 
 * - `CLASS_SCHEDULE`：值为 1，描述为“课程表” 
 * ### 总结 
 * - **功能**：`LessonRecord` 实体类用于表示课程记录，包含资源ID、课ID、用户ID、课程ID、房间ID和学习类型等属性。 
 * - **技术栈**：使用了 MyBatis Plus 进行数据库操作，并且通过 Lombok 减少了样板代码。 
 * - **扩展性**：通过枚举类型 `LearnedType` 来管理不同的学习类型，方便维护和扩展。 
 * 这段代码是一个典型的用于数据持久化的 Java 实体类，适用于需要记录用户学习行为的场景。 
**/
package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.*;

/**
 * 课程记录
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
@TableName("oj_lesson_record")
public class LessonRecord extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课id
	 *
	 * @see String
	 */
	private String lessonId;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	private String courseId;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	private String roomId;

	/**
	 * 学习类型
	 *
	 * @see Integer
	 */
	private Integer learnedType;

	public enum LearnedType implements GenericEnum {

		COURSE(0, "课程"),

		CLASS_SCHEDULE(1, "课程表");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		LearnedType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
