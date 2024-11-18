package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 红蓝模板数据
 *
 * @author SN
 * @see Serializable
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/07
 */
@Data
public class RbTemplateData implements Serializable {

	/**
     * 资源id
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
     * 预技能
     *
     * @see String
     */
	private String preSkill;

	/**
     * 训练技能
     *
     * @see String
     */
	private String trainSkill;

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
     * 持续时间
     *
     * @see Integer
     */
	private Integer durationTime;

	/**
     * 图标
     *
     * @see String
     */
	private String logo;

}
