package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class GroupSceneReq implements Serializable {

	@ApiModelProperty("课堂组编号")
	@NotBlank
	private String groupId;

}
