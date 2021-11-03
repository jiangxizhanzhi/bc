package com.cyberpeace.cr.teamtrain.model.room;

import java.io.Serializable;
import java.util.Date;

import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;

import lombok.Data;

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

	public boolean isUnopend() {
		return trainingStatus.equals(TeamRoom.TrainingStatusCode.UNOPENED.getCode());
	}

	public boolean isOpening() {
		return trainingStatus.equals(TeamRoom.TrainingStatusCode.OPENING.getCode());
	}

	public boolean isEnded() {
		return trainingStatus.equals(TeamRoom.TrainingStatusCode.ENDED.getCode());
	}
}
