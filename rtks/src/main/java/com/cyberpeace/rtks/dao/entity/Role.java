package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 角色
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@TableName("rtks_role")
public class Role extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 岗位所属层级的id
	 */
	@TableField(value = "level_id")
	private String levelId;

	/**
	 * 技能体系
	 */
	private String category;

	/**
	 * 任务id列表
	 */
	@TableField(value = "task_ids")
	private String taskIds;

	/**
	 * 知识id列表
	 */
	@TableField(value = "knowledge_ids")
	private String knowledgeIds;

	/**
	 * 知识id权重
	 */
	@TableField(value = "knowledge_id_weights")
	private String knowledgeIdWeights;

	/**
	 * 技能id列表
	 */
	@TableField(value = "skill_ids")
	private String skillIds;

	/**
	 * 技能id权重
	 */
	@TableField(value = "skill_id_weights")
	private String skillIdWeights;

	/**
	 * 能力id列表
	 */
	@TableField(value = "ability_ids")
	private String abilityIds;

	/**
	 * 仅保存顺序，不可用作关联
	 */
	@TableField(value = "knowledge_courses")
	private String knowledgeCourses;

	/**
	 * 知识权重
	 */
	private Integer knowledgeWeight;

	/**
	 * 仅保存顺序，不可用作关联
	 */
	private String skillMissions;

}
