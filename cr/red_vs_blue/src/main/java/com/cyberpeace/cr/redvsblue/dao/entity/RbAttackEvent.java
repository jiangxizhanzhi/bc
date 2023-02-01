package com.cyberpeace.cr.redvsblue.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RbAttackEvent implements Serializable {

	// 攻击阶段数据

	@ApiModelProperty(value = "")
	private String resourceId;

	@ApiModelProperty(value = "角色")
	private Integer group;

	@ApiModelProperty(value = "是否可见")
	private Boolean visible;

	@ApiModelProperty(value = "攻防类型")
	private String type;

	@ApiModelProperty(value = "攻防组别")
	private String groupType;

	@ApiModelProperty(value = "攻击组")
	private String sourceGroup;

	@ApiModelProperty(value = "攻击ip")
	private String sourceIp;

	@ApiModelProperty(value = "攻击名")
	private String sourceName;

	@ApiModelProperty(value = "攻击机")
	private String sourceMachine;

	@ApiModelProperty(value = "防御组")
	private String destGroup;

	@ApiModelProperty(value = "防御ip")
	private String destIp;

	@ApiModelProperty(value = "防御名")
	private String destName;

	@ApiModelProperty(value = "防御机")
	private String destMachine;

	@ApiModelProperty(value = "攻击行为")
	private String attackAction;

	@ApiModelProperty(value = "事件时间")
	private String eventTime;

	@ApiModelProperty(value = "事件描述")
	private String eventDescribe;
//
//	public RbAttackDefenseStage(RbTemplate template, BusinessTemplate businessTemplate) {
//		this.name = businessTemplate.getName();
//		this.category = template.getSubType();
//		this.description = businessTemplate.getDescription();
//		this.createUser = businessTemplate.getCreateUserId();
//		this.createTime = businessTemplate.getCreateTime();
//		this.difficult = businessTemplate.getDifficulty();
//		this.durationTime = template.getDurationTime();
//		this.preSkill = JsonUtil.toJsonObjectArray(template.getPreSkill(), String.class);
//		this.trainSkill = JsonUtil.toJsonObjectArray(template.getTrainSkill(), String.class);
//		this.logo = businessTemplate.getLogo();
//	}
//
//	public RbAttackDefenseStage(RbTemplateData rbTemplateData) {
//
//		this.name = rbTemplateData.getName();
//		this.category = rbTemplateData.getSubType();
//		this.description = rbTemplateData.getDescription();
//		this.createUser = rbTemplateData.getCreateUserId();
//		this.createTime = rbTemplateData.getCreateTime();
//		this.difficult = rbTemplateData.getDifficulty();
//		this.durationTime = rbTemplateData.getDurationTime();
//		this.preSkill = JsonUtil.toJsonObjectArray(rbTemplateData.getPreSkill(), String.class);
//		this.trainSkill = JsonUtil.toJsonObjectArray(rbTemplateData.getTrainSkill(), String.class);
//		this.logo = rbTemplateData.getLogo();
//	}
}
