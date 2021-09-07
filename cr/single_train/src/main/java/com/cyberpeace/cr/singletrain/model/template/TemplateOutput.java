package com.cyberpeace.cr.singletrain.model.template;

import com.cyberpeace.cr.cebase.model.crevent.CrEventOutput;
import com.cyberpeace.cr.crbase.model.MissionScheme;
import com.cyberpeace.integratation.model.SceneConfig;

import lombok.Data;

@Data
public class TemplateOutput {

	private CrEventOutput crEvent;

	private SceneConfig sceneConfig;

	private MissionScheme missionScheme;

}
