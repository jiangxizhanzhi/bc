package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 移动组用户请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class MoveGroupUserReq implements Serializable {

	/**
	 * 原分组
	 */
	@ApiModelProperty("原分组")
	@NotBlank
	private String fromGroupId;

	/**
	 * 目的分组
	 */
	@ApiModelProperty("目的分组")
	@NotBlank
	private String toGroupId;

	/**
	 * 用户编号
	 */
	@ApiModelProperty("用户编号")
	@NotBlank
	private String userId;

	/**
	 * 课堂编号
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

}
