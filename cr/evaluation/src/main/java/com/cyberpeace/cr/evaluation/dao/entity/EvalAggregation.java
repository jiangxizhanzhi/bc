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
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@TableName(value = "cr_eval_aggregation")
@Data
public class EvalAggregation extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 关联指标项id
	 *
	 * @see String
	 */
	private String evalIndicatorId;

	/**
	 * 关联采集变量id
	 *
	 * @see String
	 */
	private String collectionIds;

	/**
	 * 聚合算法id
	 *
	 * @see String
	 */
	private String aggregatingAlgorithmId;

	/**
	 * 聚合算法参数
	 *
	 * @see String
	 */
	private String aggregatingAlgorithmParams;

	/**
	 * 矩阵内容
	 *
	 * @see String
	 */
	private String matrixData;

}