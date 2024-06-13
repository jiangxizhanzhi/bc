package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("cr_eval_situation_guide_scheme")
public class EvalGuideScheme extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 试训id
	 */
	private String testId;

	/**
	 * 实例id
	 */
	private String testInstanceId;

	/**
	 * 事件名称
	 */
	private String name;

	/**
	 * 类型1:任务导调 2:手动导调
	 */
	private Integer type;

	/**
	 * 攻击类型
	 */
	private Integer attackType;

	/**
	 * 攻击源
	 */
	private String source;

	/**
	 * 攻击目标
	 */
	private String target;

	/**
	 * 攻击结果 1:成功 2:失败
	 */
	private Integer result;

	/**
	 * 步骤id
	 */
	private String stepId;

	/**
	 * 是否可编辑
	 */
	private Integer isEditable;


}