package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 提交答案请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class SubmitAnswerReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	@NotBlank
	private String resourceId;

	/**
	 * 提交答案
	 */
	@ApiModelProperty("用户提交答案")
	@NotBlank
	private String submitAnswer;

}
