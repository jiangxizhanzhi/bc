package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OperationRecordRes implements Serializable {

	@ApiModelProperty("练习id")
	private String practiceId;

	@ApiModelProperty("题目名称")
	private String title;

	@ApiModelProperty("知识点")
	private String knowledge;

	@ApiModelProperty("习题集")
	private String setName;

	@ApiModelProperty("难度")
	private Integer difficulty;

	@ApiModelProperty("完成状态")
	private Boolean solved;

	@ApiModelProperty("更新时间")
	private String time;

	@ApiModelProperty("知识点")
	private List<String> knowledgeList;

	private String tag;

	@ApiModelProperty("题型")
	private Integer category;
}
