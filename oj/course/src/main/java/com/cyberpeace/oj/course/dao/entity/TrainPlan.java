package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.model.CommonContext;

import lombok.Data;


/**
 * 训练计划
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("oj_train_plan")
public class TrainPlan extends BaseTable {

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
	 * 创建用户id
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 任务设置id
	 *
	 * @see String
	 */
	public static String TASK_SET_ID = "task_set_id";

	public static String defaultTaskSetName() {
		return CommonContext.isChinese() ? "默认任务集" : "Task Set";
	}
}
