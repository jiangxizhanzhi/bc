package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练统计返回体
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class TrainingCountRsp implements Serializable {

	/**
	 * 训练类型
	 */
	@ApiModelProperty(value = "训练类型")
	private Integer appId;

	/**
	 * 训练类型名称
	 */
	@ApiModelProperty(value = "训练类型名称")
	private String appName;

	/**
	 * 计数
	 */
	@ApiModelProperty(value = "计数")
	private Integer count;

	public TrainingCountRsp(Integer appId, String appName, Integer count) {
		this.appId = appId;
		this.appName = appName;
		this.count = count;
	}
}
