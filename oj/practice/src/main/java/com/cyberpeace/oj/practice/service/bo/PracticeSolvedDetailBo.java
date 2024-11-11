package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 练习解决详情博
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PracticeSolvedDetailBo extends PracticeNoSolvedBo {

	/**
	 * 正确答案
	 */
	@ApiModelProperty("正确答案")
	private String correctAnswer;

	/**
	 * 关键词
	 */
	@ApiModelProperty("关键字")
	private String keyword;

	/**
	 * 提交答案
	 */
	@ApiModelProperty("用户提交的答案")
	private String submitAnswer;

}
