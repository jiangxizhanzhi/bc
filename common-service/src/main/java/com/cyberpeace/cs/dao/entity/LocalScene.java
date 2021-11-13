package com.cyberpeace.cs.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("local_scene")
public class LocalScene extends BaseTable {

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
	private Date destoryTime;

	/**
	 *
	 */
	private String destoryMode;

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
