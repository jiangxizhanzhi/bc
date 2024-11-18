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
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
@ApiModel("基础数据")
public class EventDataReq {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotBlank
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述", required = true)
	@NotNull
	private String description;

	/**
	 * 类别
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "训练类型", required = true)
	@NotNull
	private Integer category;

	/**
	 * 难
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "训练难度", required = true)
	@NotNull
	private Integer difficult;

	/**
	 * 持续时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "建议时间, 单位:h", required = true)
	@NotNull
	private Integer durationTime;

	/**
	 * 预技能
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "前置技能", required = true)
	@NotNull
	private List<String> preSkill;

	/**
	 * 训练技能
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "训练技能", required = true)
	@NotNull
	private List<String> trainSkill;

	/**
	 * 场景配置数据
	 *
	 * @see SceneConfigData
	 */
	@ApiModelProperty(value = "场景拓扑", required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty("LOGO")
	private String logo;
}
