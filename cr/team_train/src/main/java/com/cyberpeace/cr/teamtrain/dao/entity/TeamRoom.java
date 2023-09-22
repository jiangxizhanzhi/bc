package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	/**
	 * 复盘用的场景id，每次关闭场景时更新
	 */
	private String replaySceneId;

	/**
	 * 行业态势攻击路径
	 */
	private String areaPath;

	/**
	 * 该训练过程的所有场景id
	 */
	private String sceneIdRecord;


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

	public List<String> getSceneIds() {
		if (this.sceneIdRecord == null) {
			return new ArrayList<>();
		}
		return JsonUtil.toJsonObjectArray(this.sceneIdRecord, String.class);
	}

	public void setSceneIds(List<String> sceneIds) {
		this.sceneIdRecord = JsonUtil.toJsonString(sceneIds);
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
}