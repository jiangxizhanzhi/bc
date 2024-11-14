package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 团队模板
 *
 * @author SN
 * @date 2024/11/14
 */
@TableName(value = "cr_team_template")
@Data
public class TeamTemplate extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;

	/**
	 * 前置技能
	 */
	private String preSkills;

	/**
	 * 训练技能
	 */
	private String trainSkills;

	/**
	 * 岗位配置
	 */
	private String roleConfig;

	/**
	 * 子类型
	 */
	private Integer subType;

	/**
	 * 时间线配置
	 */
	private String timeLineConfig;

	/**
	 * 持续时间
	 */
	private Integer durationTime;

	/**
	 * 人数
	 */
	private Integer peopleNumber;

	/**
	 * 行业态势配置
	 */
	private String situationConfig;

}