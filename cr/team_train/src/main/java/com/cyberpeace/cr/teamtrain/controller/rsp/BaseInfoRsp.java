package com.cyberpeace.cr.teamtrain.controller.rsp;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

	@ApiModelProperty(value = "建议人数")
	private Integer peopleNumber;

	@ApiModelProperty(value = "（兼容旧版本前端）")
	private String sceneManagerId;

	@ApiModelProperty(value = "场景配置")
	private JSONObject sceneConfigData;

	@ApiModelProperty(value = "LOGO")
	private String logo;

	@ApiModelProperty(value = "场景类型")
	private Integer sceneType;

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
		this.logo = businessTemplate.getLogo();
		this.sceneType = template.getSceneType();
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
		this.logo = teamTemplateData.getLogo();
		this.sceneType = teamTemplateData.getSceneType();
	}
}
