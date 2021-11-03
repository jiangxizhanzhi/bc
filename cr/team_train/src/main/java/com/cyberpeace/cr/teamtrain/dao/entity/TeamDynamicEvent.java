package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 
 * @TableName cr_team_dynamic_event
 */
@TableName(value = "cr_team_dynamic_event")
@Data
public class TeamDynamicEvent extends BaseTable {

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
	private String actionType;

	/**
	 * 
	 */
	private String actionTime;

	/**
	 * 
	 */
	private String actionContent;

	/**
	 * 
	 */
	private String missionId;

	/**
	 * 
	 */
	private String userId;

}