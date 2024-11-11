package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 移动群用户请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class MoveGroupUserReq implements Serializable {

	/**
	 * 从群id
	 */
	@ApiModelProperty("原分组")
	@NotBlank
	private String fromGroupId;

	/**
	 * 自群id
	 */
	@ApiModelProperty("目的分组")
	@NotBlank
	private String toGroupId;

	/**
	 * 用户id
	 */
	@ApiModelProperty("用户编号")
	@NotBlank
	private String userId;

	/**
	 * 课堂id
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

}
