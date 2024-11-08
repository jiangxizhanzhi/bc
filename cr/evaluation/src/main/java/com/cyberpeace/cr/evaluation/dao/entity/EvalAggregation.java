package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * eval 聚合
 *
 * @author SN
 * @date 2024/11/08
 */
@TableName(value = "cr_eval_aggregation")
@Data
public class EvalAggregation extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 关联指标项id
	 */
	private String evalIndicatorId;

	/**
	 * 关联采集变量id
	 */
	private String collectionIds;

	/**
	 * 聚合算法id
	 */
	private String aggregatingAlgorithmId;

	/**
	 * 聚合算法参数
	 */
	private String aggregatingAlgorithmParams;

	/**
	 * 矩阵内容
	 */
	private String matrixData;

}