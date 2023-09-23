package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练统计总体情况
 */
@Data
public class TrainingStatisticsRsp implements Serializable {

	@ApiModelProperty(value = "未完成训练统计")
	private List<TrainingCountRsp> incomplete;

	@ApiModelProperty(value = "已完成训练统计")
	private List<TrainingCountRsp> completed;

	@ApiModelProperty(value = "相关训练")
	private ListRsp<TrainingData> relation;

	@ApiModelProperty(value = "训练类型分布")
	private Map<Integer, Integer> trainingTypeMap;
}
