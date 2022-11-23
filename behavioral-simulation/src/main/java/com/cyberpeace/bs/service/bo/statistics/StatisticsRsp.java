package com.cyberpeace.bs.service.bo.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StatisticsRsp implements Serializable {

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "计数")
	private Integer count;

	public StatisticsRsp(Integer count) {
		this.name = "cpcloud_behavior";
		this.count = count;
	}
}
