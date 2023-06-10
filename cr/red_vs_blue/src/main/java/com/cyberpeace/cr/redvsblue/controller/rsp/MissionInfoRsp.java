package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionInfoRsp implements Serializable {

	@ApiModelProperty("任务id")
	private String resourceId;

	@ApiModelProperty("任务名称")
	private String title;

	@ApiModelProperty("选项")
	private String option;

	@ApiModelProperty("任务类别")
	private String category;

	@ApiModelProperty("任务描述")
	private String description;

	@ApiModelProperty("任务状态")
	private Integer missionStatus;

	@ApiModelProperty("任务类型")
	private Integer type;

	@ApiModelProperty("任务技能分数")
	private String skillPoints;

	@ApiModelProperty("任务题解")
	private String writeUp;

	@ApiModelProperty("任务分数")
	private Integer score;

	@ApiModelProperty("答案")
	private String answer;

	@ApiModelProperty("评估状态")
	private Integer evaluateStatus;

	@ApiModelProperty("拒绝列表")
	private List rejectLog;

	@ApiModelProperty("用户答案")
	private String userAnswer;

	@ApiModelProperty("任务状态")
	private Integer userMissionStatus;

	@ApiModelProperty("展示题解")
	private Boolean writeUpShow;


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
