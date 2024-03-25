package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("cr_eval_action")
public class EvalAction extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 试验id
	 */
	private String testId;

	/**
	 * 关联的规则id
	 */
	private String ruleId;

	/**
	 * 事件名称
	 */
	private String name;

	/**
	 * 动作类型
	 */
	private Integer actionType;

	/**
	 * 告警事件级别
	 */
	private Integer level;

	/**
	 * 攻击类型级别
	 */
	private Integer attackType;

	/**
	 * 攻击源节点id
	 */
	private String sourceId;

	/**
	 * 攻击目标节点id
	 */
	private String targetId;

	/**
	 * 攻击结果
	 */
	private Integer result;

}