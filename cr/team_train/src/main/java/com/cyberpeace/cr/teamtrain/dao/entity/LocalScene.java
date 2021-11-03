package com.cyberpeace.cr.teamtrain.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * @TableName local_scene
 */
@TableName(value = "local_scene")
@Data
public class

LocalScene implements Serializable {

	/**
	 * 
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 
	 */
	private String mpSceneId;

	/**
	 * 
	 */
	private String businessType;

	/**
	 * 
	 */
	private String moduleType;

	/**
	 * 
	 */
	private String businessCode;

	/**
	 * 
	 */
	private Date startTime;

	/**
	 *
	 */
	private Date endTime;

	/**
	 * 
	 */
	private Date destroyTime;

	/**
	 * 
	 */
	private String destroyMode;

	/**
	 * 
	 */
	private String businessInstanceId;

	/**
	 * 
	 */
	private String sceneStatus;

	/**
	 * 
	 */
	private String pauseTime;

}