package com.cyberpeace.cr.singletrain.controller.req;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 事件数据请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("基础数据")
public class EventDataReq {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotBlank
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", required = true)
	@NotNull
	private String description;

	/**
	 * 类别
	 */
	@ApiModelProperty(value = "训练类型", required = true)
	@NotNull
	private Integer category;

	/**
	 * 难
	 */
	@ApiModelProperty(value = "训练难度", required = true)
	@NotNull
	private Integer difficult;

	/**
	 * 持续时间
	 */
	@ApiModelProperty(value = "建议时间, 单位:h", required = true)
	@NotNull
	private Integer durationTime;

	/**
	 * 预技能
	 */
	@ApiModelProperty(value = "前置技能", required = true)
	@NotNull
	private List<String> preSkill;

	/**
	 * 训练技能
	 */
	@ApiModelProperty(value = "训练技能", required = true)
	@NotNull
	private List<String> trainSkill;

	/**
	 * 场景配置数据
	 */
	@ApiModelProperty(value = "场景拓扑", required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

	/**
	 * 图标
	 */
	@ApiModelProperty("LOGO")
	private String logo;
}
