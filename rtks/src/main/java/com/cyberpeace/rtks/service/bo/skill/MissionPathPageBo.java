package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务路径分页Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see MissionTemplateSimple
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionPathPageBo extends MissionTemplateSimple implements Serializable {

	/**
	 * 训练类型， 用于跳转
	 *
	 * @see Integer
	 */
	@ApiModelProperty("训练类型， 用于跳转")
	private Integer trainingType;

	/**
	 * 训练id，用于跳转
	 *
	 * @see String
	 */
	@ApiModelProperty("训练id，用于跳转")
	private String trainingId;

	/**
	 * 当前用户在训练中的角色，用于点击跳转
	 *
	 * @see Integer
	 */
	@ApiModelProperty("当前用户在训练中的角色，用于点击跳转")
	private Integer roleGroup;

	/**
	 * 是否有可用场景
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否有可用场景")
	private Boolean available;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("类型")
	private Integer type;

	/**
	 * 分数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("分数")
	private Integer score;
}
