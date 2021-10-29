package com.cyberpeace.cr.teamtrain.model.template;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.integratation.model.SceneConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainTemplateInput implements Serializable {

	// private CrEventInput crEvent;

	// private SceneConfig sceneConfig;

	@ApiModelProperty(required = true)
	@NotNull
	private BaseInfoInput baseInfo;

	@ApiModelProperty(required = true)
	@NotNull
	private SceneConfig sceneConfigData;

	@ApiModelProperty(required = true)
	@NotNull
	private List<RoleListInput> roleList;

	@ApiModelProperty(required = true)
	@NotNull
	private FlowschemeInput flowSchemesData;

	@ApiModelProperty(required = true)
	@NotNull
	private GatherschemeInput gatherSchemesData;

	@ApiModelProperty(required = true)
	@NotNull
	private Object timeLineConfig = "{}";

}
