package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 培训数返回值
 * 训练统计返回体
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class TrainingCountRsp implements Serializable {

	@ApiModelProperty(value = "训练类型")
	private Integer appId;

	@ApiModelProperty(value = "训练类型名称")
	private String appName;

	@ApiModelProperty(value = "计数")
	private Integer count;

	public TrainingCountRsp(Integer appId, String appName, Integer count) {
		this.appId = appId;
		this.appName = appName;
		this.count = count;
	}
}
