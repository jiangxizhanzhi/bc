package com.cyberpeace.cr.redvsblue.dao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class RbLostHost implements Serializable {

	// 失陷主机

	@ApiModelProperty(value = "攻击日志id")
	private String resourceId;

	@ApiModelProperty(value = "角色")
	private Integer group;

	@ApiModelProperty(value = "失陷时间")
	private String lostTime;

	@ApiModelProperty(value = "失陷靶标名")
	private String hostName;

	@ApiModelProperty(value = "失陷IP")
	private String hostIp;
//
//	@ApiModelProperty(value = "难度")
//	private Integer difficult;
//
//	@ApiModelProperty(value = "持续时长")
//	private Integer durationTime;
//
//	@ApiModelProperty(value = "前置技能")
//	private List<String> preSkill;
//
//	@ApiModelProperty(value = "训练目标")
//	private List<String> trainSkill;
//
//	@ApiModelProperty(value = "（兼容旧版本前端）")
//	private String sceneManagerId;
//
//	@ApiModelProperty(value = "场景配置")
//	private JSONObject sceneConfigData;
//
//	@ApiModelProperty(value = "LOGO")
//	private String logo;
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
