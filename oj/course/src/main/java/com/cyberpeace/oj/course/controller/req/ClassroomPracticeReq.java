package com.cyberpeace.oj.course.controller.req;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课堂练习请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class ClassroomPracticeReq extends PageReqBase {

	/**
	 * 课堂id
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

	/**
	 * 课id
	 */
	@ApiModelProperty("课时编号")
	@NotBlank(message = OjBusinessErrorCode.LESSON_INFORMATION_NOT_NULL)
	private String lessonId;

	/**
	 * 组织id
	 */
	@ApiModelProperty("组织编号")
	@NotBlank(message = OjBusinessErrorCode.ORG_INFORMATION_NOT_NULL)
	private String organizationId;

	/**
	 * 是提交
	 */
	@ApiModelProperty("是否提交")
	private Boolean isCommit=null;

	/**
	 * 是标记
	 */
	@ApiModelProperty("是否批改")
	private Boolean isMark=null;

	/**
	 * 用户名
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
