package com.cyberpeace.cr.singletrain.model.template;

import com.cyberpeace.cr.cebase.model.crevent.CrEventInput;
import com.cyberpeace.cr.crbase.model.MissionScheme;
import com.cyberpeace.integratation.model.SceneConfig;

import lombok.Data;

@Data
public class TemplateInput {

	private CrEventInput crEvent;

	private SceneConfig sceneConfig;

	private MissionScheme missionScheme;

}
