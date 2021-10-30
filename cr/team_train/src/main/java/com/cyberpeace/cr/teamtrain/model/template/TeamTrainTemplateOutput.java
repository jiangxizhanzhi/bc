package com.cyberpeace.cr.teamtrain.model.template;

import java.util.List;

import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplateRole;
import com.cyberpeace.cr.teamtrain.model.role.BaseRole;

import lombok.Data;

@Data
public class TeamTrainTemplateOutput {

	private BaseInfoOutput baseInfo;

	private List<TeamTrainTemplateRole> roleList;

	private List<BaseRole> roleStructure;

	// private FlowschemeInput flowSchemesData;
	//
	//
	//
	// private GatherschemeInput gatherSchemesData;

	private Object timeLineConfig = "{}";

}
