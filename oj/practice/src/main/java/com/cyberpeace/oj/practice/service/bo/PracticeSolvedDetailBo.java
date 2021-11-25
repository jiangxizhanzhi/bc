package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PracticeSolvedDetailBo extends PracticeNoSolvedBo {

	@ApiModelProperty("正确答案")
	private String correctAnswer;

	@ApiModelProperty("关键字")
	private String keyword;

	@ApiModelProperty("用户提交的答案")
	private String submitAnswer;

}
