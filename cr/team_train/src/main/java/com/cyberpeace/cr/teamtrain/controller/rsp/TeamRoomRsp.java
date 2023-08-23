package com.cyberpeace.cr.teamtrain.controller.rsp;

import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class TeamRoomRsp implements Serializable {

	@ApiModelProperty(value = "编号")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "类别")
	private Integer category;

	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	@ApiModelProperty(value = "训练目标")
	private List<String> trainSkill;

	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	@ApiModelProperty(value = "模板id")
	private String template;

	@ApiModelProperty(value = "模板名称")
	private String templateName;

	@ApiModelProperty(value = "难度")
	private Integer difficult;

	@ApiModelProperty(value = "自己岗位，基本信息，查列表会查询其他学员角色的内容列表")
	private BaseRole trainingRoleData;

	@ApiModelProperty(value = "所有岗位(包含教员)，单独查时使用")
	private List<TeamRoomRoleRes> rolesData;

	@ApiModelProperty(value = "持续时长")
	private Integer durationTime;

	@ApiModelProperty(value = "场景开启时间")
	private Date sceneStartTime;

	@ApiModelProperty(value = "场景关闭时间")
	private Date sceneEndTime;

	@ApiModelProperty(value = "训练状态")
	private Integer trainingStatus;

	@ApiModelProperty(value = "运行时间")
	private Integer runtime;

	@ApiModelProperty(value = "节点数")
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

	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;

	@ApiModelProperty("角色头像")
	private String userAvatar;

	@ApiModelProperty(value = "场景分类")
	private Integer sceneType;
}
