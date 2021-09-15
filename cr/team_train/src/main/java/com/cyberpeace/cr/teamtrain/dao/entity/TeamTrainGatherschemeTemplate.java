package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.suit.BaseEntity;

import lombok.Data;

@Data
@TableName("cr_team_train_gatherscheme_template")
public class TeamTrainGatherschemeTemplate extends BaseEntity {

	/**
	 * 
	 */
	private Integer id;

	/**
	 * 
	 */
	private String resourceId;

	/**
	 * 
	 */
	private String scheme;

	/**
	 * 
	 */
	private Boolean tapFlow;

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
	private String crEventId;

}