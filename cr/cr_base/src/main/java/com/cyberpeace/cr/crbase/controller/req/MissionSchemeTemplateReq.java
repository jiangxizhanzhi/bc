package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionSchemeTemplateReq implements Serializable {

	@ApiModelProperty(required = true)
	private String title;

	@ApiModelProperty(required = true)
	private String description;

	@ApiModelProperty(required = true)
	private Integer template;

	@ApiModelProperty(required = true)
	private Integer color;

	@ApiModelProperty(required = true)
	private List<MissionSchemeStage> combatConfig;

}
