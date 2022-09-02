package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 红蓝实例数据
 */
@Data
public class RbRoomData implements Serializable {

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

	private String copyedTopoId;

	private String trainingRoleData;

	private Integer durationTime;

	private Date createTime;

	private Date sceneStartTime;

	private Date sceneEndTime;

	private Integer trainingStatus;

	private Integer runtime;

	private Boolean enableRecording;

	private String logo;

	private String businessTemplateId;
}
