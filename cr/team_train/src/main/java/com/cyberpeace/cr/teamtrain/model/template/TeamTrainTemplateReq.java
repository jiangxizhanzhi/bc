package com.cyberpeace.cr.teamtrain.model.template;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.integratation.model.SceneConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainTemplateReq implements Serializable {

	// private CrEventInput crEvent;

	// private SceneConfig sceneConfig;

	@ApiModelProperty(required = true)
	@NotNull
	private BaseInfoReq baseInfo;

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
