package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionPathPageBo extends MissionTemplateSimple implements Serializable {

	/**
	 * 训练类型， 用于跳转
	 */
	@ApiModelProperty("训练类型， 用于跳转")
	private Integer trainingType;

	/**
	 * 训练id，用于跳转
	 */
	@ApiModelProperty("训练id，用于跳转")
	private String trainingId;

	/**
	 * 当前用户在训练中的角色，用于点击跳转
	 */
	@ApiModelProperty("当前用户在训练中的角色，用于点击跳转")
	private Integer roleGroup;

	/**
	 * 是否有可用场景
	 */
	@ApiModelProperty("是否有可用场景")
	private Boolean available;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	private Integer type;

	/**
	 * 分数
	 */
	@ApiModelProperty("分数")
	private Integer score;
}
