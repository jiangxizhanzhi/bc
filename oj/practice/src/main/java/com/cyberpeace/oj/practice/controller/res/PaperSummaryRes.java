package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PaperSummaryRes implements Serializable {

	@ApiModelProperty("答对数量")
	private Integer right;

	@ApiModelProperty("答错数量")
	private Integer wrong;

	@ApiModelProperty("未答数量")
	private Integer notStart;

	@ApiModelProperty("题目状态")
	private List<PracticeSolvedRes> practiceSolved;
}
