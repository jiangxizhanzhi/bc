package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class EvalRuleReq implements Serializable {

	@ApiModelProperty("resourceId")
	private String resourceId;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("描述")
	private String description;

	@ApiModelProperty("得分")
	private Integer score;

	@ApiModelProperty("房间号roomId")
	@NotBlank
	private String roomId;
}
