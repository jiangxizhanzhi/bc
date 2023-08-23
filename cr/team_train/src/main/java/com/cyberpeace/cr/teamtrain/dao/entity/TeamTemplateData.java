package com.cyberpeace.cr.teamtrain.dao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/** 多表关联的团队模板
 *
 */
@Data
public class TeamTemplateData implements Serializable {

	/** cr_team_templateId
	 *
	 */
	private String resourceId;

	private String topoId;

	private String name;

	private Integer category;

	private String description;

	private Integer businessType;

	private String createUserId;

	private Date createTime;

	private Integer difficulty;

	private String preSkills;

	private String trainSkills;

	private String roleConfig;

	private Integer subType;

	private String timeLineConfig;

	private Integer durationTime;

	private Integer peopleNumber;

	private String logo;

	private Integer trainCategory;

	private Integer sceneType;

}
