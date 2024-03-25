package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("cr_eval_dimension")
public class EvalDimension extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 事件名称
	 */
	private String name;

	/**
	 * 事件类型
	 */
	private Integer eventType;

	/**
	 * 规则参数
	 */
	private String ruleParameter;

	/**
	 * 关联的规则id
	 */
	private String ruleId;

	/**
	 * 试验id
	 */
	private String testId;

}