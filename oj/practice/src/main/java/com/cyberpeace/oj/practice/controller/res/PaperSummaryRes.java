package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 试卷总结结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PaperSummaryRes implements Serializable {

	/**
	 * 答对数量
	 */
	@ApiModelProperty("答对数量")
	private Integer right;

	/**
	 * 答错数量
	 */
	@ApiModelProperty("答错数量")
	private Integer wrong;

	/**
	 * 未答数量
	 */
	@ApiModelProperty("未答数量")
	private Integer notStart;

	/**
	 * 题目状态
	 */
	@ApiModelProperty("题目状态")
	private List<PracticeSolvedRes> practiceSolved;
}
