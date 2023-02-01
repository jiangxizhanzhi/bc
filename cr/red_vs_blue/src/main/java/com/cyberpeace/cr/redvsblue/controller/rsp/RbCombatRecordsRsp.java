package com.cyberpeace.cr.redvsblue.controller.rsp;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackDefenseEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackDefenseStage;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbLostHost;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RbCombatRecordsRsp implements Serializable {


	@ApiModelProperty(value = "攻击阶段")
	private RbAttackDefenseStage attackDefenseStage;

	@ApiModelProperty(value = "失陷主机")
	private RbLostHost lostHost;

	@ApiModelProperty(value = "攻击事件")
	private RbAttackDefenseEvent attackDefenseEvent;

	@ApiModelProperty(value = "攻击事件")
	private RbAttackEvent attackEvent;

	@ApiModelProperty(value = "防御事件")
	private RbAttackEvent defenseEvent;

	@ApiModelProperty(value = "难度")
	private Integer difficult;

	@ApiModelProperty(value = "持续时长")
	private Integer durationTime;

	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	@ApiModelProperty(value = "训练目标")
	private List<String> trainSkill;

	@ApiModelProperty(value = "（兼容旧版本前端）")
	private String sceneManagerId;

	@ApiModelProperty(value = "场景配置")
	private JSONObject sceneConfigData;

	@ApiModelProperty(value = "LOGO")
	private String logo;

//	public RbCombatRecordsRsp(RbTemplate template, BusinessTemplate businessTemplate) {
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
//	public RbCombatRecordsRsp(RbTemplateData rbTemplateData) {
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
