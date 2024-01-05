package com.cyberpeace.oj.practice.service.bo.mock;

import java.util.List;

import com.cyberpeace.oj.practice.controller.res.PracticeSolvedRes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TheorySummaryByType {

	private Integer type;

	@ApiModelProperty("数量")
	private Integer count;

	@ApiModelProperty("每题分数")
	private Integer score;

	@ApiModelProperty("题目状态")
	private List<PracticeSolvedRes> practiceSolved;

}
