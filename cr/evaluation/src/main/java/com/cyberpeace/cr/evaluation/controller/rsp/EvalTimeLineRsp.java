package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvalTimeLineRsp implements Serializable {

	@ApiModelProperty("事件描述")
	private String description;

	@ApiModelProperty("事件时间")
	private Date time;

	@ApiModelProperty("事件类型:1=阶段， 2=载荷事件")
	private Integer type;
}
