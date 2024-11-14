package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单兵事件数据
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EventDataBo implements Serializable {

	/**
	 * 训练名称
	 */
	@ApiModelProperty(value = "训练名称")
	private String name;

	/**
	 * 训练简介
	 */
	@ApiModelProperty(value = "训练简介")
	private String description;

	/**
	 * 训练类别
	 */
	@ApiModelProperty(value = "训练类别")
	private Integer category;

	/**
	 * 创建用户
	 */
	@ApiModelProperty(value = "创建用户")
	private String createUser;

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
	 * 场景配置数据
	 */
	@ApiModelProperty(value = "场景配置数据")
	private JSONObject sceneConfigData;

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
	 * LOGO
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 节点数量
	 */
	@ApiModelProperty(value = "节点数量")
	private Integer nodesCount;

}
