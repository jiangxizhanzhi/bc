package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebTheoryBo extends AdminTheoryBo {

	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	@ApiModelProperty("是否完成解题")
	private Boolean isSolved;

	@ApiModelProperty("用户提交的答案")
	private String submitAnswer;

}
