package com.cyberpeace.cr.crbase.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("cr_task_evaluation")
public class CrTaskEvaluation extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String submitUserId;

	private String evaluatorId;

	private Date evaluateTime;

	private String roomId;

	private String taskId;

	private Integer evaluateStatus;

	private String reason;

	private String detail;

	private Integer score;

	private Integer missionScore;

	private String taskAnswerId;

}
