package com.cyberpeace.cr.redvsblue.controller.rsp;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
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
	 * 创建者
	 */
	@ApiModelProperty(value = "创建者")
	private String createUser;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 难度
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficult;

	/**
	 * 持续时长
	 */
	@ApiModelProperty(value = "持续时长")
	private Integer durationTime;

	/**
	 * 前置技能
	 */
	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	/**
	 * 训练目标
	 */
	@ApiModelProperty(value = "训练目标")
	private List<String> trainSkill;

	/**
	 * （兼容旧版本前端）
	 */
	@ApiModelProperty(value = "（兼容旧版本前端）")
	private String sceneManagerId;

	/**
	 * 场景配置
	 */
	@ApiModelProperty(value = "场景配置")
	private JSONObject sceneConfigData;

	/**
	 * LOGO
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 拓扑
	 */
	private String topo;

	//	联合模式
	private String uniteTaskId;

	private Date startTime;

	private Date endTime;

	private String trainTarget;

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

	public BaseInfoRsp(BusinessTemplate businessTemplate) {
		this.name = businessTemplate.getName();
		this.description = businessTemplate.getDescription();
		this.createUser = businessTemplate.getCreateUserId();
		this.createTime = businessTemplate.getCreateTime();
		this.difficult = businessTemplate.getDifficulty();
		this.logo = businessTemplate.getLogo();
		this.uniteTaskId = businessTemplate.getUniteTaskId();
		this.startTime = businessTemplate.getStartTime();
		this.endTime = businessTemplate.getEndTime();
		this.trainTarget = businessTemplate.getTrainTarget();
	}
}
