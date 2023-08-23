package com.cyberpeace.cr.teamtrain.dao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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

	private String templateName;

	private Integer difficult;

	private String trainingRoleData;

	private String copyedTopoId;

	private Integer durationTime;

	private Date sceneStartTime;

	private Date sceneEndTime;

	private Integer trainingStatus;

	private Integer runtime;

	private Boolean enableRecording;

	private String logo;

	private String businessTemplateId;

	private String roleConfig;

	private Boolean supportPartialStart;

	private Integer sceneType;
}
