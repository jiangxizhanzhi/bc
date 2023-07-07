package com.cyberpeace.cr.redvsblue.controller.req;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbTemplateListReq extends PageReqBase {

	@ApiModelProperty(value = "类别ID")
	private String category;

	@ApiModelProperty(value = "检索参数")
	private String search;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "难度")
	private Integer difficult;

	@ApiModelProperty(value = "business_template_id列表，传入字符串，多个用逗号分隔", dataType = "java.lang.String")
	private List<String> ids;
}
