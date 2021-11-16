package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionSchemeStage {

	@ApiModelProperty(required = true)
	private String stage;

	@ApiModelProperty(required = true)
	private List<MissionSchemeMission> templates;
}

@Data
class MissionSchemeMission {

	@ApiModelProperty(required = true)
	private String title;

	@ApiModelProperty(required = true)
	private String description;
}
