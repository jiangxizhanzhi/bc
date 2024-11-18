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
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@TableName(value = "cr_team_template")
@Data
public class TeamTemplate extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	private String businessTemplateId;

	/**
	 * 预科技能
	 *
	 * @see String
	 */
	private String preSkills;

	/**
	 * 训练技能
	 *
	 * @see String
	 */
	private String trainSkills;

	/**
	 * 角色配置
	 *
	 * @see String
	 */
	private String roleConfig;

	/**
	 * 子类型
	 *
	 * @see Integer
	 */
	private Integer subType;

	/**
	 * 时间线配置
	 *
	 * @see String
	 */
	private String timeLineConfig;

	/**
	 * 持续时间
	 *
	 * @see Integer
	 */
	private Integer durationTime;

	/**
	 * 人数
	 *
	 * @see Integer
	 */
	private Integer peopleNumber;

}