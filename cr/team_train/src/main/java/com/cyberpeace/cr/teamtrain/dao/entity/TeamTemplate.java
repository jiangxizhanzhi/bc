package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 
 * @TableName cr_team_template
 */
@TableName(value = "cr_team_template")
@Data
public class TeamTemplate extends BaseTable {

	/**
	 * 
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 
	 */
	private String businessTemplateId;

	/**
	 * 
	 */
	private String preSkills;

	/**
	 * 
	 */
	private String trainSkills;

	/**
	 * 
	 */
	private String roleConfig;

	/**
	 * 
	 */
	private Integer subType;

	/**
	 * 
	 */
	private String timeLineConfig;

	/**
	 *
	 */
	private Integer durationTime;

	/**
	 *
	 */
	private Integer peopleNumber;

	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String attachmentId;

}