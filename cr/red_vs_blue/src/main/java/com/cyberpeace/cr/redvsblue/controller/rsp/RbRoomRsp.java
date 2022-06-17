package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.role.BaseRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbRoomRsp implements Serializable {

	private String resourceId;

	private String name;

	private String description;

	private Integer category;

	private List<String> preSkill;

	private List<String> trainSkill;

	private Date startTime;

	private Date endTime;

	private String template;

	private String templateName;

	private Integer difficult;

	@ApiModelProperty(value = "自己岗位，基本信息，查列表会查询其他学员角色的内容列表")
	private BaseRole rolePermission;

	@ApiModelProperty(value = "所有岗位(包含教员)，单独查时使用")
	private List<RbRoomRoleRes> rolesData;

	private Integer durationTime;

	private Date sceneStartTime;

	private Date sceneEndTime;

	private Integer trainingStatus;

	private Integer runtime;

	private Integer nodesCount;

	@ApiModelProperty(value = "录屏开关")
	private Boolean enableRecording;

	@ApiModelProperty(value = "LOGO")
	private String logo;

	@ApiModelProperty(value = "后台模板id，用于查询职业框架关联")
	private String businessTemplateId;

	@ApiModelProperty(value = "是否关联 ATT&CK")
	private Boolean isAttackRelated;

	@ApiModelProperty(value = "是否关联 Engage")
	private Boolean isEngageRelated;
}
