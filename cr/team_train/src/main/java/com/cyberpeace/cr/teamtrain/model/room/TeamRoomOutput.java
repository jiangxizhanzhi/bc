package com.cyberpeace.cr.teamtrain.model.room;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.cr.teamtrain.dao.entity.CrRole;

import lombok.Data;

@Data
public class TeamRoomOutput implements Serializable {

	private String resourceId;

	private String name;

	private String description;

	private Integer category;

	private List<String> preSkill;

	private List<String> trainSkill;

	private Date startTime;

	private Date endTime;

	private String template;

	private Integer difficult;

	private List<CrRole> trainingRoleData;

	private Integer durationTime;

	private Date sceneStartTime;

	private Date sceneEndTime;

	private Integer trainingStatus;

	private Integer runtime;


}
