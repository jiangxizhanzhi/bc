package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.Date;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务评估响应
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
public class MissionEvaluationRsp {

	/**
	 * 评估编号
	 */
	@ApiModelProperty(value = "评估编号")
	private String resourceId;

	/**
	 * 评估时间
	 */
	@ApiModelProperty(value = "评估时间")
	private Date evaluateTime;

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间id")
	private String roomId;

	/**
	 * 提交用户名称
	 */
	@ApiModelProperty(value = "提交人名称")
	private String submitUserName;

	/**
	 * 提交用户岗位
	 */
	@ApiModelProperty(value = "提交人角色")
	private String submitUserRole;

	/**
	 * 提交用户岗位资源id
	 */
	@ApiModelProperty(value = "提交人角色id")
	private String submitUserRoleResourceId;

	/**
	 * 提交用户
	 */
	@ApiModelProperty(value = "提交人id")
	private String submitUser;

	/**
	 * 计算器名称
	 */
	@ApiModelProperty(value = "评估人名称")
	private String evaluatorName;

	/**
	 * 计算器
	 */
	@ApiModelProperty(value = "评估人id")
	private String evaluator;

	/**
	 * 任务id
	 */
	@ApiModelProperty(value = "任务id")
	private String missionHash;

	/**
	 * 提交日志id
	 */
	@ApiModelProperty(value = "提交记录id")
	private String submitLogHash;

	/**
	 * 评估状态
	 */
	@ApiModelProperty(value = "评估状态")
	private Integer evaluateStatus;

	/**
	 * 原因
	 */
	@ApiModelProperty(value = "评估理由")
	private Integer reason;

	/**
	 * 详情
	 */
	@ApiModelProperty(value = "评估细节")
	private String detail;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "得分")
	private Integer score;

	/**
	 * 总分
	 */
	@ApiModelProperty(value = "满分")
	private Integer totalScore;

	/**
	 * 任务数据
	 */
	@ApiModelProperty(value = "任务数据")
	private Mission missionData;

	/**
	 * 提交信息
	 */
	@ApiModelProperty(value = "提交信息")
	private SubmitInfo submitInfo;

	@Data
	public class SubmitInfo {

	/**
	 * 提交时间
	 */
		@ApiModelProperty(value = "提交时间")
		private Date createTime;

	/**
	 * 提交答案
	 */
		@ApiModelProperty(value = "提交答案")
		private String userAnswer;
	}
}
