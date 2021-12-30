package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class GroupSceneDelayReq implements Serializable {

	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

	@ApiModelProperty("是否开启场景延时 true=开启 false=不开启")
	@NotNull
	private Boolean end;

}
