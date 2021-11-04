package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.teamtrain.service.context.BaseInfo;
import com.cyberpeace.integratation.model.SceneConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainTemplateReq implements Serializable {


	@ApiModelProperty(required = true)
	@NotNull
	private BaseInfo baseInfo;

	@ApiModelProperty(required = true)
	@NotNull
	private SceneConfig sceneConfigData;

	@ApiModelProperty(required = true)
	@NotNull
	private List<RoleListReq> roleList;

	@ApiModelProperty(required = true)
	@NotNull
	private FlowschemeReq flowSchemesData;

	@ApiModelProperty(required = true)
	@NotNull
	private GatherschemeReq gatherSchemesData;

	@ApiModelProperty(required = true)
	@NotNull
	private Object timeLineConfig = "{}";

}
