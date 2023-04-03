package com.cyberpeace.cr.crbase.service.context.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TrainingStatisticsAccount implements Serializable {

	@ApiModelProperty(value = "未完成训练统计")
	private List<TrainingCountRsp> incomplete;

	@ApiModelProperty(value = "已完成训练统计")
	private List<TrainingCountRsp> completed;

	@ApiModelProperty(value = "总数")
	private Integer count;
}
