package com.cyberpeace.rtks.controller.res.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 相关训练结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RelatedTrainingRes implements Serializable {

	/**
	 * 训练id，用于跳转
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练id，用于跳转")
	private String trainingId;

	/**
	 * 训练类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "训练类型")
	private Integer trainingType;

	/**
	 * 训练难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "训练难度")
	private Integer difficulty;

	/**
	 * 训练名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练名称")
	private String trainingName;

	/**
	 * 训练状态 (0：未开始，1：已开始， 2：已结束，3：已暂停,4：恢复
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "训练状态 (0：未开始，1：已开始， 2：已结束，3：已暂停,4：恢复")
	private Integer trainingStatus;

	/**
	 * LOGO
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 当前用户在训练里的角色
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "当前用户在训练里的角色")
	private Integer roleGroup;

}
