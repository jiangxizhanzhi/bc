package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;


/**
 * 训练计划
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@TableName("oj_train_plan")
public class TrainPlan extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 创造用户id
	 */
	private String createUserId;

	/**
	 * 任务设置
	 */
	public static String TASK_SET = "Task Set";

	/**
	 * 任务设置id
	 */
	public static String TASK_SET_ID = "task_set_id";
}
