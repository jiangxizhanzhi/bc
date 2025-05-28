/**
 * 这段代码定义了一个名为 `ClassroomPracticeReq` 的 Java 类，该类用于表示课堂实践请求体。以下是对其主要特性和功能的简要分析： 
 * ### 类定义与继承 
 * - **包声明**：`com.cyberpeace.oj.course.controller.req` - 这个类位于 `oj.course.controller.req` 包中。 
 * - **继承**：`ClassroomPracticeReq` 类继承了 `PageReqBase` 类。 
 * ### 注解使用 
 * - **Lombok**： 
 * - `@Getter` 和 `@Setter` 注解自动生成 getter 和 setter 方法。 
 * - **Swagger**： 
 * - `@ApiModel` 注解用于描述这个类是一个模型对象。 
 * - `@ApiModelProperty` 注解用于描述类中的字段。 
 * - **Java Validation API**： 
 * - `@NotBlank` 注解确保某些字段不为空白（即字符串不为空且只包含空白字符）。 
 * ### 字段说明 
 * - **classroomId**：课堂ID，必须是非空字符串。 
 * - **lessonId**：课时ID，必须是非空字符串，并且如果为空，则返回 `OjBusinessErrorCode.LESSON_INFORMATION_NOT_NULL` 错误码。 
 * - **organizationId**：组织ID，必须是非空字符串，并且如果为空，则返回 `OjBusinessErrorCode.ORG_INFORMATION_NOT_NULL` 错误码。 
 * - **isCommit**：布尔值，表示是否提交作业，默认为 `null`。 
 * - **isMark**：布尔值，表示是否批改作业，默认为 `null`。 
 * - **username**：用户名或姓名。 
 * ### 方法 
 * - **setters**：为每个字段提供了 setter 方法，用于设置属性值。 
 * - `setClassroom_id` 
 * - `setLesson_id` 
 * - `setOrganization_id` 
 * - `setName` 
 * - `setIs_commit` 
 * - `setIs_mark` 
 * ### 总结 
 * `ClassroomPracticeReq` 类主要用于处理课堂实践相关的请求数据。它通过继承 `PageReqBase` 类来获取分页相关功能，并通过 Lombok 和 Swagger 注解简化了代码编写和文档生成。验证注解确保了关键字段的有效性，从而提高了数据的健壮性。 
**/
package com.cyberpeace.oj.course.controller.req;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课堂实践请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class ClassroomPracticeReq extends PageReqBase {

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

	/**
	 * 课id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
	@NotBlank(message = OjBusinessErrorCode.LESSON_INFORMATION_NOT_NULL)
	private String lessonId;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	@ApiModelProperty("组织编号")
	@NotBlank(message = OjBusinessErrorCode.ORG_INFORMATION_NOT_NULL)
	private String organizationId;

	/**
	 * 是提交
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否提交")
	private Boolean isCommit=null;

	/**
	 * 是标记
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否批改")
	private Boolean isMark=null;

	/**
	 * 用户名
	 *
	 * @see String
	 */
	@ApiModelProperty("姓名")
	private String username;

	public void setClassroom_id(String classroomId) {
		this.classroomId = classroomId;
	}
	public void setLesson_id(String lessonId) {this.lessonId = lessonId;}
	public void setOrganization_id(String organizationId) {this.organizationId = organizationId;}
	public void setName(String username) {this.username = username;}
	public void setIs_commit(Boolean isCommit) {this.isCommit = isCommit;}
	public void setIs_mark(Boolean isMark) {this.isMark = isMark;}
}
