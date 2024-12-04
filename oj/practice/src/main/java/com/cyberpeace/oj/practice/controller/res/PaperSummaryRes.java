package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 纸总结结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class PaperSummaryRes implements Serializable {

	/**
	 * 右
	 *
	 * @see Integer
	 */
	@ApiModelProperty("答对数量")
	private Integer right;

	/**
	 * 错
	 *
	 * @see Integer
	 */
	@ApiModelProperty("答错数量")
	private Integer wrong;

	/**
	 * 未开始
	 *
	 * @see Integer
	 */
	@ApiModelProperty("未答数量")
	private Integer notStart;

	/**
	 * 练习解决
	 *
	 * @see List<PracticeSolvedRes>
	 */
	@ApiModelProperty("题目状态")
	private List<PracticeSolvedRes> practiceSolved;
}
