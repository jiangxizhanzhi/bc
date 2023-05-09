package com.cyberpeace.bs.service.bo.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统计数据响应
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class StatisticsRsp implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 计数
	 */
	@ApiModelProperty(value = "计数")
	private Integer count;

	public StatisticsRsp(Integer count) {
		this.name = "cpcloud_behavior";
		this.count = count;
	}
}
