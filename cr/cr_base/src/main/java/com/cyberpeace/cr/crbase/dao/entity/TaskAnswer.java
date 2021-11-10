package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("cr_task_answer")
public class TaskAnswer extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String roomId;

	private String answer;

	private String answerExtension;

	private String createUserId;

	private String taskId;

	private Integer businessType;

}
