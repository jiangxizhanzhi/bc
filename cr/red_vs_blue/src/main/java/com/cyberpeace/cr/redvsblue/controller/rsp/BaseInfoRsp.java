package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseInfoRsp implements Serializable {

	@NotBlank
	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "类别")
	private Integer category;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "创建者")
	private String createUser;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

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

	private String topo;

	public BaseInfoRsp(RbTemplate template, BusinessTemplate businessTemplate) {
		this.name = businessTemplate.getName();
		this.category = template.getSubType();
		this.description = businessTemplate.getDescription();
		this.createUser = businessTemplate.getCreateUserId();
		this.createTime = businessTemplate.getCreateTime();
		this.difficult = businessTemplate.getDifficulty();
		this.durationTime = template.getDurationTime();
		this.preSkill = JsonUtil.toJsonObjectArray(template.getPreSkill(), String.class);
		this.trainSkill = JsonUtil.toJsonObjectArray(template.getTrainSkill(), String.class);
		this.logo = businessTemplate.getLogo();
	}

	public BaseInfoRsp(RbTemplateData rbTemplateData) {

		this.name = rbTemplateData.getName();
		this.category = rbTemplateData.getSubType();
		this.description = rbTemplateData.getDescription();
		this.createUser = rbTemplateData.getCreateUserId();
		this.createTime = rbTemplateData.getCreateTime();
		this.difficult = rbTemplateData.getDifficulty();
		this.durationTime = rbTemplateData.getDurationTime();
		this.preSkill = JsonUtil.toJsonObjectArray(rbTemplateData.getPreSkill(), String.class);
		this.trainSkill = JsonUtil.toJsonObjectArray(rbTemplateData.getTrainSkill(), String.class);
		this.logo = rbTemplateData.getLogo();
	}
}
