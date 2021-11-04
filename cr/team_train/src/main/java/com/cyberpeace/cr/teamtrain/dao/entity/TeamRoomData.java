package com.cyberpeace.cr.teamtrain.dao.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/** 多表关联的团队实例
 *
 */
@Data
public class TeamRoomData implements Serializable {

	private String resourceId;

	private String name;

	private String description;

	private Integer category;

	private String preSkill;

	private String trainSkill;

	private Date startTime;

	private Date endTime;

	private String template;

	private Integer difficult;

	private String trainingRoleData;

	private Integer durationTime;

	private Date sceneStartTime;

	private Date sceneEndTime;

	private Integer trainingStatus;

	private Integer runtime;

	public boolean isUnopened() {
		return trainingStatus.equals(TeamRoom.TrainingStatusCode.UNOPENED.getCode());
	}

	public boolean isOpening() {
		return trainingStatus.equals(TeamRoom.TrainingStatusCode.OPENING.getCode());
	}

	public boolean isEnded() {
		return trainingStatus.equals(TeamRoom.TrainingStatusCode.ENDED.getCode());
	}
}
