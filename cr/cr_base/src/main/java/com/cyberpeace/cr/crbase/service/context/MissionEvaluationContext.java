package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.Mission;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 任务评估模型
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionEvaluationContext {

	/**
	 * submit_log_hash
	 */
	private String resourceId;

	/**
	 * 评估时间
	 */
	private Date evaluateTime;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 提交用户
	 */
	private String submitUser;

	/**
	 * 提交用户岗位
	 */
	private String submitUserRole;

	/**
	 * 提交用户岗位id
	 */
	private String submitUserRoleId;

	/**
	 * evaluator
	 */
	private String evaluatorId;

	/**
	 * 任务哈希
	 */
	private String missionHash;

	/**
	 * 评估状态
	 */
	private Integer evaluateStatus;

	/**
	 * 原因
	 */
	private Integer reason;

	/**
	 * 详情
	 */
	private String detail;

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 任务
	 */
	private Mission mission;

	/**
	 * 任务id
	 */
	private String taskId;

	/**
	 * 答案
	 */
	private String answer;

	/**
	 * 答案外延
	 */
	private String answerExtension;

	/**
	 * 任务分数
	 */
	private Integer missionScore;

	/**
	 * 创建时间
	 */
	private Date createTime;
}
