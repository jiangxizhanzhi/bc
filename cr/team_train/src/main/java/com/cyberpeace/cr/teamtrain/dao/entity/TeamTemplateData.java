package com.cyberpeace.cr.teamtrain.dao.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 多表关联的团队模板
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TeamTemplateData implements Serializable {

	/**
	 * cr_team_templateId
	 */
	private String resourceId;

	/**
	 * 拓扑id
	 */
	private String topoId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 类别
	 */
	private Integer category;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;

	/**
	 * 创造用户id
	 */
	private String createUserId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 困难
	 */
	private Integer difficulty;

	/**
	 * 预科技能
	 */
	private String preSkills;

	/**
	 * 训练技能
	 */
	private String trainSkills;

	/**
	 * 角色配置
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
	 * 图标
	 */
	private String logo;

}
