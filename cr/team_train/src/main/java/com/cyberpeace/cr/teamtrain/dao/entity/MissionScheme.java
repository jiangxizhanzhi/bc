package com.cyberpeace.cr.teamtrain.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * @TableName cr_mission_scheme
 */
@TableName(value = "cr_mission_scheme")
@Data
public class MissionScheme implements Serializable {

	/**
	 * 
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 
	 */
	private String createUserId;

	/**
	 * 
	 */
	private String schemeData;

}