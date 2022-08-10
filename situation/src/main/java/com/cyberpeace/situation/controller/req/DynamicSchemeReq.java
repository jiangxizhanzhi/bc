package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.model.DynamicStage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DynamicSchemeReq implements Serializable {

	@ApiModelProperty(value = "资源id", required = true)
	@NotBlank
	private String resourceId;

	@ApiModelProperty("场景id")
	@NotBlank
	private String sceneId;

	@ApiModelProperty("是否为实例场景")
	private Boolean isInstance;

	@ApiModelProperty("动效方案名称")
	@NotBlank
	private String name;

	@ApiModelProperty("动效方案数据")
	private List<DynamicStage> schemeData;
}
