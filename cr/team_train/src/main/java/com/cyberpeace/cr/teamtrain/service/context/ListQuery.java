package com.cyberpeace.cr.teamtrain.service.context;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class ListQuery extends PageReqBase {

	@ApiModelProperty(value = "类别ID")
	private String category;

	@ApiModelProperty(value = "检索参数")
	private String search;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "难度")
	private Integer difficult;

	private List<String> ids;

}