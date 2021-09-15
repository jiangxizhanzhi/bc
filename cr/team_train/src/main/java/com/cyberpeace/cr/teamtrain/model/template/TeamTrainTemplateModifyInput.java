package com.cyberpeace.cr.teamtrain.model.template;

import com.cyberpeace.cr.cebase.model.crevent.CrEventModifyInput;

import lombok.Data;

@Data
public class TeamTrainTemplateModifyInput extends TeamTrainTemplateInput {

	private CrEventModifyInput crEvent;
}
