package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamRoomRsp implements Serializable {

	private String resourceId;

	private String name;

	private String description;

	private Integer category;

	private List<String> preSkill;

	private List<String> trainSkill;

	private Date startTime;

	private Date endTime;

	private String template;

	private Integer difficult;

	@ApiModelProperty(value = "自己岗位，基本信息，查列表会查询其他学员角色的内容列表")
	private BaseRole trainingRoleData;

	@ApiModelProperty(value = "所有岗位(包含教员)，单独查时使用")
	private List<CrRole> rolesData;

	private Integer durationTime;

	private Date sceneStartTime;

	private Date sceneEndTime;

	private Integer trainingStatus;

	private Integer runtime;

	private Integer nodesCount;
}
