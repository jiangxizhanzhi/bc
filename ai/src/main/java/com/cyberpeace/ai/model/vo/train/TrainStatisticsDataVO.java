package com.cyberpeace.ai.model.vo.train;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCountRsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("实操题各方向题目练习情况")
public class TrainStatisticsDataVO {

	/**
	 * 未完成训练统计
	 */
	@ApiModelProperty(value = "未完成训练统计")
	private List<TrainingCountRsp> incomplete;

	/**
	 * 完成
	 */
	@ApiModelProperty(value = "已完成训练统计")
	private List<TrainingCountRsp> completed;

	/**
	 * 训练类型分布
	 */
	@ApiModelProperty(value = "训练类型分布")
	private Map<Integer, Integer> trainingTypeMap;

}