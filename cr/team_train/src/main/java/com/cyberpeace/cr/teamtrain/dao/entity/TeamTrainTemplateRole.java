package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.suit.BaseEntity;

import lombok.Data;

@Data
@TableName("cr_team_train_template_role")
public class TeamTrainTemplateRole extends BaseEntity {

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private Integer roleGroup;

	/**
	 * 
	 */
	private String missionsScheme;

	/**
	 * 
	 */
	private String nodes;

	/**
	 * 
	 */
	private String crEventId;

}