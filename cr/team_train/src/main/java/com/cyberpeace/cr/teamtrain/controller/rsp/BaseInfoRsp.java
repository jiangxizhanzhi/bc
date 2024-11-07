package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础信息响应
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class BaseInfoRsp implements Serializable {

	/**
	 * 名称
	 */
	@NotBlank
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 类别
	 */
	@ApiModelProperty(value = "类别")
	private Integer category;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 创建用户
	 */
	@ApiModelProperty(value = "创建者")
	private String createUser;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 难
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficult;

	/**
	 * 持续时间
	 */
	@ApiModelProperty(value = "持续时长")
	private Integer durationTime;

	/**
	 * 预技能
	 */
	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	/**
	 * 训练技能
	 */
	@ApiModelProperty(value = "训练目标")
	private List<String> trainSkill;

	/**
	 * 人数
	 */
	@ApiModelProperty(value = "建议人数")
	private Integer peopleNumber;

	/**
	 * 场景经理id
	 */
	@ApiModelProperty(value = "（兼容旧版本前端）")
	private String sceneManagerId;

	/**
	 * 场景配置数据
	 */
	@ApiModelProperty(value = "场景配置")
	private JSONObject sceneConfigData;

	/**
	 * 图标
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 拓扑
	 */
	private String topo;

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
	}
}
