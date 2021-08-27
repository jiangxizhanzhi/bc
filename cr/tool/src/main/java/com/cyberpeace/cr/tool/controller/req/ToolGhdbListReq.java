package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class ToolGhdbListReq extends PageReqBase {

	@ApiModelProperty(value = "标题")
	@Getter
	@Setter
	private String title;

	@ApiModelProperty(value = "作者")
	@Getter
	@Setter
	private String author;
}
