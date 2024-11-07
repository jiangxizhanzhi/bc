package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 记录响应基础
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RecordRspBase implements Serializable {

	/**
	 * 训练时间
	 */
	@ApiModelProperty(value = "训练时间")
	private Date trainingTime;

	/**
	 * 训练时间字符串
	 */
	@ApiModelProperty(value = "训练时间")
	private String trainingTimeString;

	/**
	 * 时间成本
	 */
	@ApiModelProperty(value = "训练时长")
	private String timeCost;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "得分")
	private Integer score;
}
