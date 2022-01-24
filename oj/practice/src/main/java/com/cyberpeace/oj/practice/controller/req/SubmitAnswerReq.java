package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class SubmitAnswerReq implements Serializable {

	@ApiModelProperty("资源编号")
	@NotBlank
	private String resourceId;

	@ApiModelProperty("用户提交答案")
	@NotBlank
	private String submitAnswer;

}
