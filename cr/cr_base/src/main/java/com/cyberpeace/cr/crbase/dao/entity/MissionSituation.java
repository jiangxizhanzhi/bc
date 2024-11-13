package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 任务态势
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
@TableName("cr_mission_situation")
public class MissionSituation extends BaseTable implements Serializable {

	/**
	 * 任务模板id
	 */
	@TableId(type = IdType.NONE, value = "mission_template_id")
	private String missionTemplateId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 态势id
	 */
	private Integer tsId;

	/**
	 * 态势类型
	 */
	private String tsType;

	/**
	 * 态势描述
	 */
	private String tsDesc;

	/**
	 * 目标设备
	 */
	private String targetDevice;

	/**
	 * 源设备
	 */
	private String sourceDevice;

	/**
	 * 面积
	 */
	private String area;

	/**
	 * 额外动效
	 */
	private String extraEffect;
}
