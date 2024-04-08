package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalPersonTestRsp implements Serializable {

	@ApiModelProperty("训练任务id")
	private String testId;

	@ApiModelProperty("训练任务名称")
	private String name;

	@ApiModelProperty("训练任务状态:0=未开始，1=进行中")
	private Integer status;

	@ApiModelProperty("训练时长")
	private Integer durationTime;

	@ApiModelProperty("时间")
	private String time;
}
