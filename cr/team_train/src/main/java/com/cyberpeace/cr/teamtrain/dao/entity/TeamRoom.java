package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@TableName(value = "cr_team_room")
@Data
public class TeamRoom extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String teamTemplateId;

	private String teamTemplateName;

	private String name;

	private String description;

	private Integer subType;

	private String preSkills;

	private String trainSkills;

	private Date startTime;

	private Date endTime;

	private Integer difficulty;

	private Integer trainingStatus;

	private String timeLineConfig;

	private String createUserId;

	private String modifyUserId;

	private String localSceneId;

	private String copyedTopoId;

	/**
	 * 是否开启录屏
	 */
	private Boolean enableRecording;

	private String logo;

	private Integer trainCategory;

	public TeamRoom() {}

	public TeamRoom(TeamTrainRoomContext input, TeamTemplate template) {
		this.teamTemplateId = template.getResourceId();
		this.name = input.getName();
		this.description = input.getDescription();
		this.subType = input.getCategory();
		this.preSkills = JsonUtil.toJsonString(input.getPreSkill());
		this.trainSkills = JsonUtil.toJsonString(input.getTrainSkill());
		this.startTime = input.getStartTime();
		this.endTime = input.getEndTime();
		this.difficulty = input.getDifficult();
		this.timeLineConfig = template.getTimeLineConfig();
		this.enableRecording = input.getEnableRecording();
		this.createUserId = createUserId;
		this.modifyUserId = modifyUserId;
		this.localSceneId = localSceneId;
		this.logo = input.getLogo();
	}

	public boolean isUnopened() {
		return trainingStatus.equals(TeamRoom.TrainingStatusCode.UNOPENED.getCode());
	}

	public boolean isOpening() {
		return trainingStatus.equals(TeamRoom.TrainingStatusCode.OPENING.getCode());
	}

	public boolean isEnded() {
		return trainingStatus.equals(TeamRoom.TrainingStatusCode.ENDED.getCode());
	}

	public enum TrainingStatusCode {

		UNOPENED(0, "未开始"), OPENING(1, "已开始"), ENDED(2, "已结束");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private TrainingStatusCode(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	/**
	 * 区分红队训练/蓝队训练
	 */
	public enum TrainCategory {
		RED(1, "红队"), BLUE(2, "蓝队"), DAILY(3, "日常");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		TrainCategory(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	public OperationModule getModule() {
		return getTrainCategory().equals(TrainCategory.RED.getCode())
				? OperationModule.RED_TEAM
				: getTrainCategory().equals(TrainCategory.BLUE.getCode()) ?
				OperationModule.BLUE_TEAM : OperationModule.DAILY_TRAIN;
	}

	public String getLogMsg() {
		return getTrainCategory().equals(TrainCategory.RED.getCode())
				? LogContent.RED_TEAM_TRAIN_ROOM.getMsg()
				: getTrainCategory().equals(TrainCategory.BLUE.getCode()) ?
				LogContent.BLUE_TEAM_TRAIN_ROOM.getMsg() :LogContent.DAILY_TRAIN_ROOM.getMsg() ;
	}
}