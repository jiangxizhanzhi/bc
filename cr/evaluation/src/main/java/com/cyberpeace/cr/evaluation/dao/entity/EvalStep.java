package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;


@Data
@TableName("cr_eval_step")
public class EvalStep extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 阶段名称
	 */
	private String name;

	/**
	 * 验证过程
	 */
	private String process;

	/**
	 * 载荷设置
	 */
	private String setting;

	/**
	 * 试验id
	 */
	private String testId;


}