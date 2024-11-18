package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * EVAL 系列
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@TableName(value = "cr_eval_collection")
@Data
public class EvalCollection extends BaseTable {

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
	 * 关联指标项id
	 *
	 * @see String
	 */

	private String evalIndicatorId;

	/**
	 * 格式化算法id
	 *
	 * @see String
	 */
	private String formatAlgorithmId;

	/**
	 * 格式化算法参数
	 *
	 * @see String
	 */
	private String formatAlgorithmParams;

	/**
	 * 检测算法id
	 *
	 * @see String
	 */
	private String detectionAlgorithmId;

	/**
	 * 检测算法参数
	 *
	 * @see String
	 */
	private String detectionAlgorithmParams;

	/**
	 * 评分算法id
	 *
	 * @see String
	 */
	private String scoreAlgorithmId;

	/**
	 * 评分算法参数
	 *
	 * @see String
	 */
	private String scoreAlgorithmParams;

}