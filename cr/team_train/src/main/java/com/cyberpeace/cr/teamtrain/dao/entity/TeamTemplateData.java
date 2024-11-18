package com.cyberpeace.cr.teamtrain.dao.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 多表关联的团队模板
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TeamTemplateData implements Serializable {

	/**
	 * cr_team_templateId
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 拓扑id
	 *
	 * @see String
	 */
	private String topoId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 类别
	 *
	 * @see Integer
	 */
	private Integer category;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	private Integer businessType;

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	private String businessTemplateId;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	private Date createTime;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

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

	/**
	 * 图标
	 *
	 * @see String
	 */
	private String logo;

}
