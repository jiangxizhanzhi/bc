package com.cyberpeace.rtks.controller.res.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RelatedTrainingRes implements Serializable {

	/**
	 * 训练id，用于跳转
	 */
	@ApiModelProperty(value = "训练id，用于跳转")
	private String trainingId;

	/**
	 * 训练类型
	 */
	@ApiModelProperty(value = "训练类型")
	private Integer trainingType;

	/**
	 * 训练难度
	 */
	@ApiModelProperty(value = "训练难度")
	private Integer difficulty;

	/**
	 * 训练名称
	 */
	@ApiModelProperty(value = "训练名称")
	private String trainingName;

	/**
	 * 训练状态 (0：未开始，1：已开始， 2：已结束，3：已暂停,4：恢复
	 */
	@ApiModelProperty(value = "训练状态 (0：未开始，1：已开始， 2：已结束，3：已暂停,4：恢复")
	private Integer trainingStatus;

	/**
	 * LOGO
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 当前用户在训练里的角色
	 */
	@ApiModelProperty(value = "当前用户在训练里的角色")
	private Integer roleGroup;

}
