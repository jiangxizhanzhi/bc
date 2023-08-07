package com.cyberpeace.cr.crbase.controller.rsp;

import com.cyberpeace.cr.crbase.dao.entity.Mission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MissionEvaluationRsp {

	/**
	 *submit_log_hash
	 */
	@ApiModelProperty(value = "评估编号")
	private String resourceId;

	@ApiModelProperty(value = "评估时间")
	private Date evaluateTime;

	@ApiModelProperty(value = "房间id")
	private String roomId;

	@ApiModelProperty(value = "提交人名称")
	private String submitUserName;

	@ApiModelProperty(value = "提交人角色")
	private String submitUserRole;

	@ApiModelProperty(value = "提交人角色id")
	private String submitUserRoleResourceId;

	@ApiModelProperty(value = "提交人id")
	private String submitUser;

	@ApiModelProperty(value = "评估人名称")
	private String evaluatorName;

	@ApiModelProperty(value = "评估人id")
	private String evaluator;

	@ApiModelProperty(value = "任务id")
	private String missionHash;

	@ApiModelProperty(value = "提交记录id")
	private String submitLogHash;

	@ApiModelProperty(value = "评估状态")
	private Integer evaluateStatus;

	@ApiModelProperty(value = "评估理由")
	private Integer reason;

	@ApiModelProperty(value = "评估细节")
	private String detail;

	@ApiModelProperty(value = "得分")
	private Integer score;

	@ApiModelProperty(value = "满分")
	private Integer totalScore;

	@ApiModelProperty(value = "任务数据")
	private Mission missionData;

	@ApiModelProperty(value = "提交信息")
	private SubmitInfo submitInfo;

	@ApiModelProperty(value = "是否一二三血")
	private String rank;

	@ApiModelProperty(value = "额外加分")
	private Integer rankScore;

	@Data
	public class SubmitInfo {

		@ApiModelProperty(value = "提交时间")
		private Date createTime;

		@ApiModelProperty(value = "提交答案")
		private String userAnswer;
	}
}
