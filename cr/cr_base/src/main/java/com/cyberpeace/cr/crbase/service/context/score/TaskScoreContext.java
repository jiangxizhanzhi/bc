package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;

import lombok.Data;

/**
 * 任务得分上下文
 * 驳回算未完成
 *
 * @author SN
 */
@Data
public class TaskScoreContext implements Serializable {

	private String missionId;

	private String missionTitle;

	private Integer taskScore;

	private Integer missionScore;

	private Integer missionType;

	private Integer taskStatus;

	public boolean isSuccessful() {
		return this.taskStatus.equals(CrTask.TaskStatus.SUCCESS.getCode());
	}

	public boolean isFailed() {
		return this.taskStatus.equals(CrTask.TaskStatus.FAIL.getCode());
	}

	public boolean isNotEvaluated() {
		return this.taskStatus.equals(CrTask.TaskStatus.WAIT.getCode());
	}

}
