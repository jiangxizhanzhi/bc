package com.cyberpeace.cr.crbase.service;

import lombok.Data;

import java.util.Date;

/**
 * 任务提交记录
 */
@Data
public class MissionTaskSummitContext {


	private String missionId;

	private String resourceId;

	private String roomId;

	private String taskId;

	private String answer;

	private String answerExtension;

	private String evaluatorId;

	private Date evaluateTime;

	private Integer evaluateStatus;

	private Integer reason;

	private String detail;

	private Integer score;

	private Integer missionScore;

	private String evalTemplateId;
}
