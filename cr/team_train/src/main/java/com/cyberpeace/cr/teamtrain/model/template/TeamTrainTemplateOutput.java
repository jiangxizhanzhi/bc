package com.cyberpeace.cr.teamtrain.model.template;

import com.cyberpeace.cr.cebase.model.crevent.CrEventOutput;
import com.cyberpeace.integratation.model.SceneConfig;

import lombok.Data;

@Data
public class TeamTrainTemplateOutput {

	private CrEventOutput crEvent;

	private SceneConfig sceneConfig;

}
