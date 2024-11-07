package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练统计总体情况
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TrainingStatisticsRsp implements Serializable {

	/**
	 * 不完全
	 */
	@ApiModelProperty(value = "未完成训练统计")
	private List<TrainingCountRsp> incomplete;

	/**
	 * 完成
	 */
	@ApiModelProperty(value = "已完成训练统计")
	private List<TrainingCountRsp> completed;

	/**
	 * 关系
	 */
	@ApiModelProperty(value = "相关训练")
	private ListRsp<TrainingData> relation;

	/**
	 * 训练类型地图
	 */
	@ApiModelProperty(value = "训练类型分布")
	Map<Integer, Integer> trainingTypeMap;
}
