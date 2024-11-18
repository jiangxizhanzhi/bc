package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.Date;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务评估响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class MissionEvaluationRsp {

	/**
	 * submit_log_hash
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "评估编号")
	private String resourceId;

	/**
	 * 评估时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "评估时间")
	private Date evaluateTime;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "房间id")
	private String roomId;

	/**
	 * 提交用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "提交人名称")
	private String submitUserName;

	/**
	 * 提交用户角色
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "提交人角色")
	private String submitUserRole;

	/**
	 * 提交用户角色资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "提交人角色id")
	private String submitUserRoleResourceId;

	/**
	 * 提交用户
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "提交人id")
	private String submitUser;

	/**
	 * 计算器名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "评估人名称")
	private String evaluatorName;

	/**
	 * 计算器
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "评估人id")
	private String evaluator;

	/**
	 * 任务哈希
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务id")
	private String missionHash;

	/**
	 * 提交日志哈希
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "提交记录id")
	private String submitLogHash;

	/**
	 * 评估状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "评估状态")
	private Integer evaluateStatus;

	/**
	 * 原因
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "评估理由")
	private Integer reason;

	/**
	 * 详情
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "评估细节")
	private String detail;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "得分")
	private Integer score;

	/**
	 * 总分
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "满分")
	private Integer totalScore;

	/**
	 * 任务数据
	 *
	 * @see Mission
	 */
	@ApiModelProperty(value = "任务数据")
	private Mission missionData;

	/**
	 * 提交信息
	 *
	 * @see SubmitInfo
	 */
	@ApiModelProperty(value = "提交信息")
	private SubmitInfo submitInfo;

	@Data
	public class SubmitInfo {

		@ApiModelProperty(value = "提交时间")
		private Date createTime;

		@ApiModelProperty(value = "提交答案")
		private String userAnswer;
	}
}
