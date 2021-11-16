package com.cyberpeace.cr.teamtrain.service.context;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.cyberpeace.integratation.model.SceneConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainTemplateContext extends BasicTemplate {

	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

	@ApiModelProperty(required = true)
	@NotNull
	private SceneConfig sceneConfigData;

	@ApiModelProperty(required = true)
	@NotNull
	private String roleList;

	// @ApiModelProperty(required = true)
	// @NotNull
	// private FlowschemeReq flowSchemesData;
	//
	// @ApiModelProperty(required = true)
	// @NotNull
	// private GatherschemeReq gatherSchemesData;

	@ApiModelProperty(required = true)
	@NotNull
	private Object timeLineConfig = "{}";
}
