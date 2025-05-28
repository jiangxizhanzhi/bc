/**
 * 这段代码定义了一个名为 `Note` 的实体类，该类继承自 `AuthTable`。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.ojbase.dao.entity` 
 * - **类名**：`Note` 
 * - **继承关系**：`Note` 继承自 `AuthTable` 
 * ### 注解 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter`，简化了属性的 getter 和 setter 方法的生成。 
 * - 使用了 MyBatis Plus 的注解 `@TableName` 来指定数据库表名。 
 * - 使用了 Swagger 的注解 `@ApiModelProperty` 来为属性添加描述。 
 * ### 属性 
 * - `resourceId`：资源ID，使用 UUID 作为主键。 
 * - `noteType`：笔记类型，枚举类型为 `NoteType`。 
 * - `businessType`：业务类型。 
 * - `content`：笔记内容。 
 * - `userId`：用户ID。 
 * - `businessId`：业务ID。 
 * - `isPublic`：是否公开，布尔值。 
 * - `isSubmit`：是否提交，布尔值。 
 * ### 枚举类型 
 * - `NoteType`：表示笔记类型的枚举。 
 * - `NOTE`：值为 1，描述为“笔记”。 
 * - `REPORT`：值为 2，描述为“实验报告”。 
 * - `NoteResourceType`：表示笔记资源类型的枚举。 
 * - `COURSE`：值为 1，描述为“课程”。 
 * - `LESSON`：值为 2，描述为“课时”。 
 * - `OPTION`：值为 3，描述为“实操题”。 
 * - `LESSON_PAPER`：值为 4，描述为“课后练习”。 
 * - `THEORY`：值为 5，描述为“理论题”。 
 * ### 总结 
 * 这个 `Note` 类主要用于表示笔记相关的数据模型，包含笔记的基本信息（如资源ID、内容等）以及一些扩展信息（如笔记类型、业务类型等）。通过继承 `AuthTable`，可以实现一些通用的权限控制功能。枚举类型 `NoteType` 和 `NoteResourceType` 提供了笔记类型的分类。 
**/
package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * 注意
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see AuthTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_note")
public class Note extends AuthTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("笔记编号")
	private String resourceId;

	/**
	 * 注释类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("笔记类型")
	private Integer noteType;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("业务类型")
	private Integer businessType;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("内容")
	private String content;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	@ApiModelProperty("业务编号")
	private String businessId;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@TableField("public")
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 是提交
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("实验报告是否提交")
	private Boolean isSubmit;

	public enum NoteType implements GenericEnum {

		NOTE(1, "笔记"), REPORT(2, "实验报告");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		NoteType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	public enum NoteResourceType implements GenericEnum {

		COURSE(1, "课程"),
		LESSON(2, "课时"),
		OPTION(3, "实操题"),
		LESSON_PAPER(4, "课后练习"),
		THEORY(5, "理论题");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		NoteResourceType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
