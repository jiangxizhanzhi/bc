package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 评估注意请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class EvaluateNoteReq implements Serializable {

	/**
	 * 注意id
	 */
	@ApiModelProperty("笔记编号")
	@NotBlank
	private String noteId;

	/**
	 * 评估评论
	 */
	@ApiModelProperty("评语")
	@NotBlank
	private String evaluateComment;

	/**
	 * 通过
	 */
	@ApiModelProperty("是否通过")
	@NotNull
	private Boolean passed;

	/**
	 * 得分
	 */
	@ApiModelProperty("得分")
	@NotNull
	private Double score;

}
