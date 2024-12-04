package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实践解决详情博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PracticeNoSolvedBo
 * @since 1.0.0
 */
@Data
public class PracticeSolvedDetailBo extends PracticeNoSolvedBo {

	/**
	 * 正确答案
	 *
	 * @see String
	 */
	@ApiModelProperty("正确答案")
	private String correctAnswer;

	/**
	 * 关键词
	 *
	 * @see String
	 */
	@ApiModelProperty("关键字")
	private String keyword;

	/**
	 * 提交答案
	 *
	 * @see String
	 */
	@ApiModelProperty("用户提交的答案")
	private String submitAnswer;

}
