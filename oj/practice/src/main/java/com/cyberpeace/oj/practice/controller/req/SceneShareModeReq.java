package com.cyberpeace.oj.practice.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SceneShareModeReq implements Serializable {

	@ApiModelProperty("隔离模式设置 1=个人 2=共享")
	@NotNull
	private Integer sceneShareMode;

	@ApiModelProperty("资源编号")
	@NotBlank
	private String resourceId;

}
