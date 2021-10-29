package com.cyberpeace.cr.teamtrain.model.room;

import java.io.Serializable;
import java.util.Date;

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

}
