package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateRole;

import lombok.Data;

@Data
public class TeamTrainTemplateRsp {

	private String resourceId;

	private BaseInfoRsp baseInfo;

	private List<TeamTrainTemplateRole> roleList;

	private List<BaseRole> roleStructure;

	private Object timeLineConfig = "{}";

}
