package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @TableName cr_eval_indicator
 */
@TableName(value = "cr_eval_indicator")
@Getter
@Setter
public class EvalIndicator extends BaseTable {

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
	 * 数据类型
	 */
	private Integer datatype;

	/**
	 * 指标属性
	 */
	private Integer attribute;

	/**
	 * 指标属性值
	 */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Integer attributeValue;

	/**
	 * 连接线
	 */
	private String connlist;

	/**
	 * 聚合算法id
	 */
	private String aggregatingAlgorithmId;

	/**
	 * 聚合算法参数
	 */
	private String aggregatingAlgorithmParams;

	/**
	 * 关联聚合项id
	 */
	private String aggregationIds;

	/**
	 * 关联变量项id
	 */
	private String collectionIds;

	/**
	 * 创建人
	 */
	private String createUserId;

	/**
	 * 矩阵内容
	 */
	private String matrixData;
}