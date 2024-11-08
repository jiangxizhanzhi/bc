package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 纸总结结果
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class PaperSummaryRes implements Serializable {

	/**
	 * 右
	 */
	@ApiModelProperty("答对数量")
	private Integer right;

	/**
	 * 错
	 */
	@ApiModelProperty("答错数量")
	private Integer wrong;

	/**
	 * 未开始
	 */
	@ApiModelProperty("未答数量")
	private Integer notStart;

	/**
	 * 练习解决
	 */
	@ApiModelProperty("题目状态")
	private List<PracticeSolvedRes> practiceSolved;
}
