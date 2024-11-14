package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 红蓝模板数据
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbTemplateData implements Serializable {

	/**
	 * 资源id
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
	 * 创建用户id
	 */
	private String createUserId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 难度
	 */
	private Integer difficulty;

	/**
	 * 前置技能
	 */
	private String preSkill;

	/**
	 * 训练技能
	 */
	private String trainSkill;

	/**
	 * 岗位配置
	 */
	private String roleConfig;

	/**
	 * 子类型
	 */
	private Integer subType;

	/**
	 * 持续时间
	 */
	private Integer durationTime;

	/**
	 * 图标
	 */
	private String logo;

}
