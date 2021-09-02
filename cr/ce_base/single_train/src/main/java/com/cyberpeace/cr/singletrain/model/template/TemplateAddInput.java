package com.cyberpeace.cr.singletrain.model.template;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.cebase.model.crevent.CrEventAddInput;
import com.cyberpeace.cr.crbase.model.MissionScheme;
import com.cyberpeace.cr.crbase.model.SceneConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TemplateAddInput extends TemplateInput {

	@ApiModelProperty(required = true)
	@NotNull
	private CrEventAddInput crEvent;

	@ApiModelProperty(required = true)
	@NotNull
	private SceneConfig sceneConfig;

	@ApiModelProperty(required = true)
	@NotNull
	private MissionScheme missionScheme;

}
