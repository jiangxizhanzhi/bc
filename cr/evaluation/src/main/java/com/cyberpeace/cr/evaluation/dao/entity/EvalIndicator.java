package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 
 * @TableName cr_eval_indicator
 */
@TableName(value = "cr_eval_indicator")
@Data
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
	private String attribute;
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
}