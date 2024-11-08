package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;

import lombok.Data;

/**
 * 任务得分上下文
 * 驳回算未完成
 *
 * @author SN
 * @date 2023/05/23
 */
@Data
public class TaskScoreContext implements Serializable {

	/**
	 * 任务id
	 */
	private String missionId;

	/**
	 * 任务名称
	 */
	private String missionTitle;

	/**
	 * 任务分数
	 */
	private Integer taskScore;

	/**
	 * 任务分数
	 */
	private Integer missionScore;

	/**
	 * 任务类型
	 */
	private Integer missionType;

	/**
	 * 任务状态
	 */
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
