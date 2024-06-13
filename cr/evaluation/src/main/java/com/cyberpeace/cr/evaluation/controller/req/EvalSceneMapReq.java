package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.File;

@Data
public class EvalSceneMapReq {

	@ApiModelProperty(value = "测绘json文件", required = true)
	private File topoFile;

	@ApiModelProperty(value = "测绘json文件", required = true)
	private File serverFile;

	@ApiModelProperty(value = "测绘json文件", required = true)
	private File imageFile;
}
