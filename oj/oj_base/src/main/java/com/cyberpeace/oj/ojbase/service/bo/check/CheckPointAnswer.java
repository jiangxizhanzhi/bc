package com.cyberpeace.oj.ojbase.service.bo.check;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckPointAnswer implements Serializable {

	@ApiModelProperty("提交的答案")
	private String submitAnswer;

	@ApiModelProperty("习题编号")
	private String practiceId;
}
