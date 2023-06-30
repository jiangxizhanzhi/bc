package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单兵事件数据
 */
@Data
public class EventDataBo implements Serializable {

	@ApiModelProperty(value = "训练名称")
	private String name;

	@ApiModelProperty(value = "训练简介")
	private String description;

	@ApiModelProperty(value = "训练类别")
	private Integer category;

	@ApiModelProperty(value = "创建用户")
	private String createUser;

	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	@ApiModelProperty(value = "训练目标")
	private List<String> trainSkill;

	@ApiModelProperty(value = "场景配置数据")
	private JSONObject sceneConfigData;

	@ApiModelProperty(value = "难度")
	private Integer difficult;

	@ApiModelProperty(value = "持续时长")
	private Integer durationTime;

	@ApiModelProperty(value = "LOGO")
	private String logo;

	@ApiModelProperty(value = "节点数量")
	private Integer nodesCount;

}
