package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class EvaluateNoteReq implements Serializable {

	@ApiModelProperty("笔记编号")
	@NotBlank
	private String noteId;

	@ApiModelProperty("评语")
	@NotBlank
	private String evaluateComment;

	@ApiModelProperty("是否通过")
	@NotNull
	private Boolean passed;

	@ApiModelProperty("得分")
	@NotNull
	private Double score;

}
