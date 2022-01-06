package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionSchemeStage {

	@ApiModelProperty(required = true)
	@NotEmpty
	private String stage;

	@ApiModelProperty(required = true)
	@NotEmpty
	@Valid
	private List<MissionSchemeMission> templates;
}

@Data
class MissionSchemeMission {

	@ApiModelProperty(required = true)
	@NotEmpty
	private String title;

	@ApiModelProperty(required = true)
	@NotEmpty
	private String description;
}
