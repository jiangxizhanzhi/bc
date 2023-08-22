package com.cyberpeace.cr.teamtrain.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class TeamTemplateListReq extends PageReqBase {

	@ApiModelProperty(value = "类别ID")
	private String category;

	@ApiModelProperty(value = "检索参数")
	private String search;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "难度")
	private Integer difficult;

	@ApiModelProperty(value = "红蓝训练区分, 1=红;2=蓝")
	private Integer trainCategory;

	@ApiModelProperty(value = "场景类型")
	private Integer sceneType;
}
