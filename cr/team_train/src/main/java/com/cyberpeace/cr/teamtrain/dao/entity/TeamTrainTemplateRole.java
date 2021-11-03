package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("cr_team_train_template_role")
public class TeamTrainTemplateRole extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**角色名称
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