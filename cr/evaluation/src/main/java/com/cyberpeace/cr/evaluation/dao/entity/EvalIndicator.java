package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 评估指标
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@TableName(value = "cr_eval_indicator")
@Data
public class EvalIndicator extends BaseTable {

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
	 * 数据类型
	 *
	 * @see Integer
	 */
	private Integer datatype;

	/**
	 * 指标属性
	 *
	 * @see Integer
	 */
	private Integer attribute;

	/**
	 * 指标属性值
	 *
	 * @see Integer
	 */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Integer attributeValue;

	/**
	 * 连接线
	 *
	 * @see String
	 */
	private String connlist;

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
	 * 关联聚合项id
	 *
	 * @see String
	 */
	private String aggregationIds;

	/**
	 * 关联变量项id
	 *
	 * @see String
	 */
	private String collectionIds;

	/**
	 * 创建人
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 矩阵内容
	 *
	 * @see String
	 */
	private String matrixData;
}