package com.cyberpeace.cr.teamtrain.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.teamtrain.controller.req.FlowschemeReq;
import com.cyberpeace.cr.teamtrain.controller.req.GatherschemeReq;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplateRole;
import com.cyberpeace.integratation.model.SceneConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainTemplateContext {

	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

	@ApiModelProperty(required = true)
	@NotNull
	private BaseInfo baseInfo;

	@ApiModelProperty(required = true)
	@NotNull
	private SceneConfig sceneConfigData;

	@ApiModelProperty(required = true)
	@NotNull
	private List<TeamTrainTemplateRole> roleList;

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
