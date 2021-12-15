package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class SceneStatusReq implements Serializable {

	@ApiModelProperty("资源编号")
	@NotBlank
	private String resourceId;

	@ApiModelProperty("1=启动 3=暂停")
	@NotNull
	private Integer status;

}
