package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务信息响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class MissionInfoRsp {

	/**
	 * 任务id
	 */
	@ApiModelProperty("任务id")
	private String resourceId;

	/**
	 * 任务名称
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 选项
	 */
	@ApiModelProperty("选项")
	private String option;

	/**
	 * 任务类别
	 */
	@ApiModelProperty("任务类别")
	private String category;

	/**
	 * 任务描述
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 任务状态
	 */
	@ApiModelProperty("任务状态")
	private Integer missionStatus;

	/**
	 * 任务类型
	 */
	@ApiModelProperty("任务类型")
	private Integer type;

	/**
	 * 任务技能分数
	 */
	@ApiModelProperty("任务技能分数")
	private String skillPoints;

	/**
	 * 任务题解
	 */
	@ApiModelProperty("任务题解")
	private String writeUp;

	/**
	 * 任务分数
	 */
	@ApiModelProperty("任务分数")
	private Integer score;

	/**
	 * 答案
	 */
	@ApiModelProperty("答案")
	private String answer;

	/**
	 * 评估状态
	 */
	@ApiModelProperty("评估状态")
	private Integer evaluateStatus;

	/**
	 * 拒绝列表
	 */
	@ApiModelProperty("拒绝列表")
	private List rejectLog;

	/**
	 * 用户答案
	 */
	@ApiModelProperty("用户答案")
	private String userAnswer;

	/**
	 * 任务状态
	 */
	@ApiModelProperty("任务状态")
	private Integer userMissionStatus;

	/**
	 * 展示题解
	 */
	@ApiModelProperty("展示题解")
	private Boolean writeUpShow;

	/**
	 * 备注
	 */
	private String remark;

	public void initUserMissionStatus(TaskContext taskContext) {
		if (CrTask.TaskStatus.RELEASED.getCode().equals(taskContext.getStatus())) {
			this.setUserMissionStatus(
					MissionsStatistic.MissionStatisticalState.MISSION_NOT_STARTED.getCode());
		} else if (taskContext.isFinished()) {
			this.setUserMissionStatus(
					MissionsStatistic.MissionStatisticalState.MISSION_COMPLETED.getCode());
		} else {
			this.setUserMissionStatus(
					MissionsStatistic.MissionStatisticalState.MISSION_ONGOING.getCode());
		}
	}
}
