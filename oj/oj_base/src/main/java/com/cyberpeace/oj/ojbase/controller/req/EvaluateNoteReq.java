package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 评估笔记请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class EvaluateNoteReq implements Serializable {

	/**
	 * 笔记编号
	 */
	@ApiModelProperty("笔记编号")
	@NotBlank
	private String noteId;

	/**
	 * 评语
	 */
	@ApiModelProperty("评语")
	@NotBlank
	private String evaluateComment;

	/**
	 * 是否通过
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
