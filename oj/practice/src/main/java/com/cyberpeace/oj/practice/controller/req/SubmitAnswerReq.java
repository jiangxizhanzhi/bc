package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 提交答案请求体
 *
 * @author SN
 * @see Serializable
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class SubmitAnswerReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
     * 资源id
     *
     * @see String
     */
	@ApiModelProperty("资源编号")
	@NotBlank
	private String resourceId;

	/**
     * 提交答案
     *
     * @see String
     */
	@ApiModelProperty("用户提交答案")
	@NotBlank
	private String submitAnswer;

}
