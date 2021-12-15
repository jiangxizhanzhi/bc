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

import lombok.Data;

@Data
public class BaseInfoRsp implements Serializable {

	@NotBlank
	private String name;

	private Integer category;

	private String description;

	private String createUser;

	private Date createTime;

	private Integer difficult;

	private Integer durationTime;

	private List<String> preSkill;

	private List<String> trainSkill;

	private String sceneManagerId;

	private JSONObject sceneConfigData;

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
	}
}
