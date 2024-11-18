package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 记录响应基础
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RecordRspBase implements Serializable {

	/**
	 * 训练时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "训练时间")
	private Date trainingTime;

	/**
	 * 训练时间字符串
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练时间")
	private String trainingTimeString;

	/**
	 * 时间成本
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练时长")
	private String timeCost;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "得分")
	private Integer score;
}
