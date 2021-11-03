package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 
 * @TableName cr_team_score
 */
@TableName(value = "cr_team_score")
@Data
public class TeamScore extends BaseTable {

	/**
	 * 
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 
	 */
	private String teamInstanceId;

	/**
	 * 
	 */
	private String userId;

	/**
	 * 
	 */
	private String trainingScore;

	/**
	 * 
	 */
	private String trainingPercentage;

	/**
	 * 
	 */
	private String skillPercentage;

	/**
	 * 
	 */
	private String skillScore;

	/**
	 * 
	 */
	private String comment;

	/**
	 * 
	 */
	private String finalScore;

}