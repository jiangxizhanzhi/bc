/**
 * 这段代码定义了一个名为 `Comment` 的实体类，用于表示评论信息。以下是详细的分析和总结： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.ojbase.dao.entity` 
 * - **类名**: `Comment` 
 * - **继承关系**: 继承自 `BaseTable` 
 * - **注解**: 
 * - `@TableName("oj_comment")`: 指定该实体类对应的数据库表名为 `oj_comment`。 
 * - `@Getter` 和 `@Setter`: 自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **resource_id** (`String`): 资源 ID，使用 `@TableId` 注解指定为主键，类型为 UUID。 
 * - **replyId** (`String`): 回复 ID。 
 * - **businessId** (`String`): 业务 ID。 
 * - **themeName** (`String`): 主题名称。 
 * - **userId** (`String`): 用户 ID。 
 * - **content** (`String`): 评论内容。 
 * - **businessType** (`Integer`): 业务类型。 
 * - **rootId** (`String`): 根 ID。 
 * ### 枚举类 
 * - **CommentType**: 定义了评论的类型枚举，实现 `GenericEnum` 接口。 
 * - **字段**: 
 * - `code` (`Integer`): 评论类型的编码。 
 * - `desc` (`String`): 评论类型的描述。 
 * - **枚举值**: 
 * - `COURSE (1, "课程")` 
 * - `LESSON (2, "课时")` 
 * - `EXERCISE (3, "实操题")` 
 * - `AFTER_CLASS_EXERCISE (4, "课后练习")` 
 * - `THEORY (5, "理论题")` 
 * ### 总结 
 * - **用途**: 这个类用于表示一个评论对象，并且包含了一些基本的属性（如资源 ID、用户 ID、评论内容等）以及一个枚举类来定义评论的类型。 
 * - **技术栈**: 使用了 MyBatis Plus 来进行数据访问层的操作，通过注解简化了数据映射。 
 * - **扩展性**: 通过定义枚举类 `CommentType`，可以方便地添加或修改评论类型，同时保持代码的可读性和可维护性。 
 * 这个类的设计是标准的 Java 实体类设计，适用于数据库操作，并且具有良好的扩展性。 
**/
package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * 评论
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_comment")
public class Comment extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 答id
	 *
	 * @see String
	 */
	private String replyId;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	private String businessId;

	/**
	 * 主题名称
	 *
	 * @see String
	 */
	private String themeName;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	private String content;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	private Integer businessType;

	/**
	 * 根id
	 *
	 * @see String
	 */
	private String rootId;

	public enum CommentType implements GenericEnum {

		COURSE(1, "课程"),

		LESSON(2, "课时"),

		EXERCISE(3, "实操题"),
		AFTER_CLASS_EXERCISE(4, "课后练习"),
		THEORY(5, "理论题"),
		;

		@Getter
		private Integer code;

		@Getter
		private String desc;

		CommentType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
