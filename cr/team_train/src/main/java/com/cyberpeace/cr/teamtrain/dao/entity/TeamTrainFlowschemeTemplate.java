package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.suit.BaseEntity;

import lombok.Data;

/**
 * 
 * @TableName cr_team_train_flowscheme_template
 */
@Data
@TableName("cr_team_train_flowscheme_template")
public class TeamTrainFlowschemeTemplate extends BaseEntity {

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
	private String name;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private String scheme;

	/**
	 * 
	 */
	private String crEventId;

}