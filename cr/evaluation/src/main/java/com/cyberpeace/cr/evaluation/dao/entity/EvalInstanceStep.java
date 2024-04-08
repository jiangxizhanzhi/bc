package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.util.Date;

@Data
@TableName("cr_eval_test_instance_step")
public class EvalInstanceStep extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 试验任务id
	 */
	private String testId;

	/**
	 * 试验任务实例id
	 */
	private String testInstanceId;

	/**
	 * 步骤id
	 */
	private String stepId;

	/**
	 * 步骤序号
	 */
	private Integer stage;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

}