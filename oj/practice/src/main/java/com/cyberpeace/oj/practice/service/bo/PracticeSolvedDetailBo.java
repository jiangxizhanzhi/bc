package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 练习解决详情Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PracticeSolvedDetailBo extends PracticeNoSolvedBo {

	/**
	 * 正确答案
	 */
	@ApiModelProperty("正确答案")
	private String correctAnswer;

	/**
	 * 关键字
	 */
	@ApiModelProperty("关键字")
	private String keyword;

	/**
	 * 用户提交的答案
	 */
	@ApiModelProperty("用户提交的答案")
	private String submitAnswer;

}
