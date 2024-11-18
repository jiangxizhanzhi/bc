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
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class BaseInfoRsp implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@NotBlank
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 类别
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类别")
	private Integer category;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 创建用户
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "创建者")
	private String createUser;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 难
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficult;

	/**
	 * 持续时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "持续时长")
	private Integer durationTime;

	/**
	 * 预技能
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	/**
	 * 训练技能
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "训练目标")
	private List<String> trainSkill;

	/**
	 * 人数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "建议人数")
	private Integer peopleNumber;

	/**
	 * 场景经理id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "（兼容旧版本前端）")
	private String sceneManagerId;

	/**
	 * 场景配置数据
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty(value = "场景配置")
	private JSONObject sceneConfigData;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 拓扑
	 *
	 * @see String
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
