package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.io.Serializable;
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
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionInfoRsp implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("任务id")
	private String resourceId;

    /**
     * 标题
     *
     * @see String
     */
    @ApiModelProperty("任务名称")
	private String title;

    /**
     * 选择
     *
     * @see String
     */
    @ApiModelProperty("选项")
	private String option;

    /**
     * 类别
     *
     * @see String
     */
    @ApiModelProperty("任务类别")
	private String category;

    /**
     * 描述
     *
     * @see String
     */
    @ApiModelProperty("任务描述")
	private String description;

    /**
     * 任务状态
     *
     * @see Integer
     */
    @ApiModelProperty("任务状态")
	private Integer missionStatus;

    /**
     * 类型
     *
     * @see Integer
     */
    @ApiModelProperty("任务类型")
	private Integer type;

    /**
     * 技能点
     *
     * @see String
     */
    @ApiModelProperty("任务技能分数")
	private String skillPoints;

    /**
     * 写入向上
     *
     * @see String
     */
    @ApiModelProperty("任务题解")
	private String writeUp;

    /**
     * 得分
     *
     * @see Integer
     */
    @ApiModelProperty("任务分数")
	private Integer score;

    /**
     * 答案
     *
     * @see String
     */
    @ApiModelProperty("答案")
	private String answer;

    /**
     * 评估状态
     *
     * @see Integer
     */
    @ApiModelProperty("评估状态")
	private Integer evaluateStatus;

    /**
     * 拒绝日志
     *
     * @see List
     */
    @ApiModelProperty("拒绝列表")
	private List rejectLog;

    /**
     * 用户答案
     *
     * @see String
     */
    @ApiModelProperty("用户答案")
	private String userAnswer;

    /**
     * 用户任务状态
     *
     * @see Integer
     */
    @ApiModelProperty("任务状态")
	private Integer userMissionStatus;

    /**
     * 写入向上显示
     *
     * @see Boolean
     */
    @ApiModelProperty("展示题解")
	private Boolean writeUpShow;

    /**
     * 备注
     *
     * @see String
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
