package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练统计返回体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TrainingCountRsp implements Serializable {

	/**
	 * 应用程序id
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "训练类型")
	private Integer appId;

	/**
	 * 应用程序名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练类型名称")
	private String appName;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "计数")
	private Integer count;

	public TrainingCountRsp(Integer appId, String appName, Integer count) {
		this.appId = appId;
		this.appName = appName;
		this.count = count;
	}
}
