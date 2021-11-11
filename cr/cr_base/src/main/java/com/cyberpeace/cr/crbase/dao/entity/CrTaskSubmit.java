package com.cyberpeace.cr.crbase.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("cr_task_submit")
public class CrTaskSubmit extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String roomId;

	private String taskId;

	private String answer;

	private String answerExtension;

	private Date createTime;

	private String evaluatorId;

	private Date evaluateTime;

	private Integer evaluateStatus;

	private Integer reason;

	private String detail;

	private Integer score;

	private Integer missionScore;

}
