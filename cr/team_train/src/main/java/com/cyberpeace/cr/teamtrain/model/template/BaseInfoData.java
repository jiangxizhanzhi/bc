package com.cyberpeace.cr.teamtrain.model.template;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BaseInfoData implements Serializable {

	/** cr_team_templateId
	 *
	 */
	private String resourceId;

	private String topoId;

	private String name;

	private Integer category;

	private String description;

	private Integer businessType;

	private String createUser;

	private Date createTime;

	private Integer difficulty;

	private String preSkills;

	private String trainSkills;

	private String roleConfig;

	private Integer subType;

	private String timeLineConfig;

	private Integer durationTime;

	private Integer peopleNumber;

}
