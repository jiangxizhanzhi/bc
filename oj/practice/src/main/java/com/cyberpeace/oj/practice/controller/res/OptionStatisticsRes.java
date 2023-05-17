package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OptionStatisticsRes implements Serializable {

	@ApiModelProperty("收藏数量")
	private Integer collection;

	@ApiModelProperty("笔记数量")
	private Integer note;
}
