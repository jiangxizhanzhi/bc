package com.cyberpeace.oj.practice.service.bo.mock;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TheorySummary {

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "倒计时，秒")
	private Integer countDown;

	@ApiModelProperty(value = "题目总数")
	private Integer count;

	@ApiModelProperty(value = "已答题数")
	private Integer solvedCount;

	@ApiModelProperty(value = "满分")
	private Integer fullMark = 100;

	@ApiModelProperty(value = "各题型")
	private Map<String, TheorySummaryByType> map;
}
