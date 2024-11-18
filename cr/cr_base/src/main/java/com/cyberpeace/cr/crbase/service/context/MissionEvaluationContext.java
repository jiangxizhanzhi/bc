package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import lombok.Data;

/**
 * 任务评估模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class MissionEvaluationContext {

	/**
	 * submit_log_hash
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 评估时间
	 *
	 * @see Date
	 */
	private Date evaluateTime;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	private String roomId;

	/**
	 * 提交用户
	 *
	 * @see String
	 */
	private String submitUser;

	/**
	 * 提交用户角色
	 *
	 * @see String
	 */
	private String submitUserRole;

	/**
	 * 提交用户角色id
	 *
	 * @see String
	 */
	private String submitUserRoleId;

	/**
	 * evaluator
	 *
	 * @see String
	 */
	private String evaluatorId;

	/**
	 * 任务哈希
	 *
	 * @see String
	 */
	private String missionHash;

	/**
	 * 评估状态
	 *
	 * @see Integer
	 */
	private Integer evaluateStatus;

	/**
	 * 原因
	 *
	 * @see Integer
	 */
	private Integer reason;

	/**
	 * 详情
	 *
	 * @see String
	 */
	private String detail;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	private Integer score;

	/**
	 * 任务
	 *
	 * @see Mission
	 */
	private Mission mission;

	/**
	 * 任务id
	 *
	 * @see String
	 */
	private String taskId;

	/**
	 * 答案
	 *
	 * @see String
	 */
	private String answer;

	/**
	 * 答案外延
	 *
	 * @see String
	 */
	private String answerExtension;

	/**
	 * 任务分数
	 *
	 * @see Integer
	 */
	private Integer missionScore;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	private Date createTime;
}
