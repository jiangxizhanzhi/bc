package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalTaskStatisticsRes implements Serializable {

	@ApiModelProperty("任务总数")
	private Integer sum;

	@ApiModelProperty("成功数量")
	private Integer success;

	@ApiModelProperty("失败")
	private Integer fail;

	@ApiModelProperty("未完成")
	private Integer inCompletion;
}
