package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PracticeWrongRes implements Serializable {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty("题目名称")
	private String content;

	@ApiModelProperty("错题人员")
	private String userName;

	@ApiModelProperty("提交时间")
	private String submitTime;

	@ApiModelProperty("题目类型")
	private Integer practiceType;

	@ApiModelProperty("题目id")
	private String practiceId;
}
