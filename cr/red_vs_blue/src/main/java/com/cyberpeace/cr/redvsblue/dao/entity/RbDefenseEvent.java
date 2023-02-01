package com.cyberpeace.cr.redvsblue.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RbDefenseEvent implements Serializable {

	// 攻击阶段数据

	@ApiModelProperty(value = "")
	private String resourceId;

	@ApiModelProperty(value = "角色")
	private Integer group;

	@ApiModelProperty(value = "攻击阶段")
	private String attackPhase;

	@ApiModelProperty(value = "")
	private String content;

//	@ApiModelProperty(value = "创建时间")
//	private Date createTime;
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
