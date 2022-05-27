package com.cyberpeace.cs.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("sjyc_task")
public class GxTask extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 任务id
	 */
	private String taskId;

	/**
	 * 任务名称
	 */
	private String name;

	/**
	 * 任务内容
	 */
	private String content;

	/**
	 * 任务目标
	 */
	private String target;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 训练时长（小时）
	 */
	private Double duration;

	/**
	 * 任务有效期（天）
	 */
	private Integer validityPeriod;

	/**
	 * 训练人员
	 */
	private String trainees;

	/**
	 * 训练人数
	 */
	private Integer traineesCount;

	/**
	 * 创建人员
	 */
	private String createUserId;

	/**
	 * 事件类型
	 */
	private Integer eventType;

	/**
	 * 事件id
	 */
	private String eventId;

	/**
	 * 任务结果
	 */
	private String result;

}
