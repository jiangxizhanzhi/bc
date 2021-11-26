package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;

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

	private Integer peopleNumber;

	private String sceneManagerId;

	private SceneRsp sceneConfigData;

	public BaseInfoRsp(TeamTemplate template, BusinessTemplate businessTemplate) {
		this.name = businessTemplate.getName();
		this.category = template.getSubType();
		this.description = businessTemplate.getDescription();
		this.createUser = businessTemplate.getCreateUserId();
		this.createTime = businessTemplate.getCreateTime();
		this.difficult = businessTemplate.getDifficulty();
		this.durationTime = template.getDurationTime();
		this.preSkill = JsonUtil.toJsonObjectArray(template.getPreSkills(), String.class);
		this.trainSkill = JsonUtil.toJsonObjectArray(template.getTrainSkills(), String.class);
		this.peopleNumber = template.getPeopleNumber();
	}

	public BaseInfoRsp(TeamTemplateData teamTemplateData) {
		this.name = teamTemplateData.getName();
		this.category = teamTemplateData.getSubType();
		this.description = teamTemplateData.getDescription();
		this.createUser = teamTemplateData.getCreateUserId();
		this.createTime = teamTemplateData.getCreateTime();
		this.difficult = teamTemplateData.getDifficulty();
		this.durationTime = teamTemplateData.getDurationTime();
		this.preSkill = JsonUtil.toJsonObjectArray(teamTemplateData.getPreSkills(), String.class);
		this.trainSkill = JsonUtil.toJsonObjectArray(teamTemplateData.getTrainSkills(),
				String.class);
		this.peopleNumber = teamTemplateData.getPeopleNumber();
	}
}
