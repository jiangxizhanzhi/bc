package com.cyberpeace.cr.teamtrain.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DailyTrainTemplateRoleRsp implements Serializable {

	@ApiModelProperty(value = "组织名称")
	private String name;

	@ApiModelProperty(value = "组织类型")
	private String type;

	private List<TeamTrainTemplateRoleRsp>  children;
}
