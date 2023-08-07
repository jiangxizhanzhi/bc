package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.Mission;
import lombok.Data;

import java.util.Date;

/**
 * 任务评估模型
 */
@Data
public class MissionEvaluationContext {

	/**
	 *submit_log_hash
	 */
	private String resourceId;

	private Date evaluateTime;

	private String roomId;

	private String submitUser;

	private String submitUserRole;

	private String submitUserRoleId;

	/**
	 * evaluator
	 */
	private String evaluatorId;

	private String missionHash;

	private Integer evaluateStatus;

	private Integer reason;

	private String detail;

	private Integer score;

	private Mission mission;

	private String taskId;

	private String answer;

	private String answerExtension;

	private Integer missionScore;

	private Date createTime;

	private String rank;

	private Integer rankScore;

}
