package com.cyberpeace.cr.evaluation.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("cr_eval_task")
public class EvalTask extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String templateId;

	private String name;

	private String taskName;

	private String localSceneId;

	private String mpSceneId;

	private String copyedTopoId;

	private Date startTime;

	private Date endTime;

	private Boolean enableRecording;

	private String createUserId;

}
