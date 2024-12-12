package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 移动群用户请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class MoveGroupUserReq implements Serializable {

	/**
	 * 从群id
	 *
	 * @see String
	 */
	@ApiModelProperty("原分组")
	@NotBlank
	private String fromGroupId;

	/**
	 * 自群id
	 *
	 * @see String
	 */
	@ApiModelProperty("目的分组")
	@NotBlank
	private String toGroupId;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	@NotBlank
	private String userId;

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

}
