package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 岗位
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("rtks_role")
public class Role extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 岗位所属层级的id
	 *
	 * @see String
	 */
	@TableField(value = "level_id")
	private String levelId;

	/**
	 * 技能体系
	 *
	 * @see String
	 */
	private String category;

	/**
	 * 任务id列表
	 *
	 * @see String
	 */
	@TableField(value = "task_ids")
	private String taskIds;

	/**
	 * 知识点id列表
	 *
	 * @see String
	 */
	@TableField(value = "knowledge_ids")
	private String knowledgeIds;

	/**
	 * 知识点id权重
	 *
	 * @see String
	 */
	@TableField(value = "knowledge_id_weights")
	private String knowledgeIdWeights;

	/**
	 * 技能id列表
	 *
	 * @see String
	 */
	@TableField(value = "skill_ids")
	private String skillIds;

	/**
	 * 技能id权重
	 *
	 * @see String
	 */
	@TableField(value = "skill_id_weights")
	private String skillIdWeights;

	/**
	 * 能力id列表
	 *
	 * @see String
	 */
	@TableField(value = "ability_ids")
	private String abilityIds;

	/**
	 * 仅保存顺序，不可用作关联
	 *
	 * @see String
	 */
	@TableField(value = "knowledge_courses")
	private String knowledgeCourses;

	/**
	 * 知识点重量
	 *
	 * @see Integer
	 */
	private Integer knowledgeWeight;

	/**
	 * 仅保存顺序，不可用作关联
	 *
	 * @see String
	 */
	private String skillMissions;

	/**
	 * 头像
	 *
	 * @see String
	 */
	private String avatar;

}
