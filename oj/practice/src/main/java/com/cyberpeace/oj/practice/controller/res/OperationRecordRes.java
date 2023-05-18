package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OperationRecordRes implements Serializable {

	@ApiModelProperty("练习id")
	private String practiceId;

	@ApiModelProperty("题目名称")
	private String title;

	@ApiModelProperty("方向")
	private String categoryName;

	@ApiModelProperty("习题集")
	private String setName;

	@ApiModelProperty("难度")
	private Integer difficulty;

	@ApiModelProperty("完成状态")
	private Boolean solved;

	@ApiModelProperty("更新时间")
	private String time;
}
