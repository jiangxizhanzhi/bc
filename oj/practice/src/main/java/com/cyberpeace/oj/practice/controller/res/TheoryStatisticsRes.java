package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TheoryStatisticsRes implements Serializable {

	@ApiModelProperty("错题数量")
	private Integer mistake;

	@ApiModelProperty("收藏数量")
	private Integer collection;

	@ApiModelProperty("笔记数量")
	private Integer note;
}
