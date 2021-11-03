package com.cyberpeace.cr.teamtrain.model.template;

import java.util.List;

import com.cyberpeace.cr.crbase.model.role.BaseRole;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplateRole;

import lombok.Data;

@Data
public class TeamTrainTemplateRsp {

	private BaseInfoRsp baseInfo;

	private List<TeamTrainTemplateRole> roleList;

	private List<BaseRole> roleStructure;

	private Object timeLineConfig = "{}";

}
