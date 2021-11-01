package com.cyberpeace.cr.teamtrain.model.template;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;

import lombok.Data;

@Data
public class BaseInfoOutput implements Serializable {

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

	public BaseInfoOutput(String name, Integer category, String description, String createUser,
			Date createTime, Integer difficult, Integer durationTime, List<String> preSkill,
			List<String> trainSkill, Integer people_number) {
		this.name = name;
		this.category = category;
		this.description = description;
		this.createUser = createUser;
		this.createTime = createTime;
		this.difficult = difficult;
		this.durationTime = durationTime;
		this.preSkill = preSkill;
		this.trainSkill = trainSkill;
		this.peopleNumber = people_number;
	}

	public BaseInfoOutput(TeamTemplate template, BusinessTemplate businessTemplate) {
		this.name = businessTemplate.getName();
		this.category = template.getSubType();
		this.description = businessTemplate.getDescription();
		this.createUser = businessTemplate.getCreateUserId();
		this.createTime = createTime;
		this.difficult = businessTemplate.getDifficulty();
		this.durationTime = template.getDurationTime();
		this.preSkill = JsonUtil.toJsonObjectArray(template.getPreSkills(), String.class);
		this.trainSkill = JsonUtil.toJsonObjectArray(template.getTrainSkills(), String.class);
		this.peopleNumber = template.getPeopleNumber();
	}

	public BaseInfoOutput(BaseInfoData baseInfoData) {
		this.name = baseInfoData.getName();
		this.category = baseInfoData.getSubType();
		this.description = baseInfoData.getDescription();
		this.createUser = baseInfoData.getCreateUser();
		this.createTime = createTime;
		this.difficult = baseInfoData.getDifficulty();
		this.durationTime = baseInfoData.getDurationTime();
		this.preSkill = JsonUtil.toJsonObjectArray(baseInfoData.getPreSkills(), String.class);
		this.trainSkill = JsonUtil.toJsonObjectArray(baseInfoData.getTrainSkills(), String.class);
		this.peopleNumber = baseInfoData.getPeopleNumber();
	}
}
