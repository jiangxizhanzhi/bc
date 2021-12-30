package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainingStatisticsRsp implements Serializable {

	@ApiModelProperty(value = "未完成训练统计")
	private List<TrainingCountRsp> incomplete;

	@ApiModelProperty(value = "已完成训练统计")
	private List<TrainingCountRsp> completed;

	@ApiModelProperty(value = "相关训练")
	private ListRsp<TrainingData> relation;

}
