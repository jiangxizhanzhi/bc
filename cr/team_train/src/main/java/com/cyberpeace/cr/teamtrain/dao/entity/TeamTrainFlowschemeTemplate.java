package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * @TableName cr_team_train_flowscheme_template
 */
@Data
@TableName("cr_team_train_flowscheme_template")
public class TeamTrainFlowschemeTemplate {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

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