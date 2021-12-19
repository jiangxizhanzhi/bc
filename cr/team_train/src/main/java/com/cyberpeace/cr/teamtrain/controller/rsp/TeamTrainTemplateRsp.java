package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.role.BaseRole;

import lombok.Data;

@Data
public class TeamTrainTemplateRsp {

	private String resourceId;

	private BaseInfoRsp baseInfo;

	private Integer peopleNumber;

	private List<TeamTrainTemplateRoleRsp> roleList;

	private List<BaseRole> roleStructure;

	private Integer nodesCount;

	private Object timeLineConfig = "{}";

}
