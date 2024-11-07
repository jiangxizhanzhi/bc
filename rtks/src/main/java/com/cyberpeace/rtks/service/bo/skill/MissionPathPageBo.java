package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务路径分页博
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MissionPathPageBo extends MissionTemplateSimple implements Serializable {

	/**
	 * 训练类型
	 */
	@ApiModelProperty("训练类型， 用于跳转")
	private Integer trainingType;

	/**
	 * 训练id
	 */
	@ApiModelProperty("训练id，用于跳转")
	private String trainingId;

	/**
	 * 角色组
	 */
	@ApiModelProperty("当前用户在训练中的角色，用于点击跳转")
	private Integer roleGroup;

	/**
	 * 可用
	 */
	@ApiModelProperty("是否有可用场景")
	private Boolean available;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	private Integer type;

	/**
	 * 得分
	 */
	@ApiModelProperty("分数")
	private Integer score;
}
