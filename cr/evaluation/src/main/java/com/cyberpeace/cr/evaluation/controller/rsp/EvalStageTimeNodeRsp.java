package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvalStageTimeNodeRsp implements Serializable {

	@ApiModelProperty("事件描述")
	private String description;

	@ApiModelProperty("事件时间")
	private String time;
}
