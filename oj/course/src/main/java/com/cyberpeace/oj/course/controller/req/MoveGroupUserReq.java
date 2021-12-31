package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class MoveGroupUserReq implements Serializable {

	@ApiModelProperty("原分组")
	@NotBlank
	private String fromGroupId;

	@ApiModelProperty("目的分组")
	@NotBlank
	private String toGroupId;

	@ApiModelProperty("用户编号")
	@NotBlank
	private String userId;

	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

}
