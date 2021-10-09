package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("rtks_role")
public class Role {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String name;

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

	@TableField(value = "task_ids")
	private String taskIds;

	@TableField(value = "knowledge_ids")
	private String knowledgeIds;

	@TableField(value = "knowledge_id_weights")
	private String knowledgeIdWeights;

	@TableField(value = "skill_ids")
	private String skillIds;

	@TableField(value = "skill_id_weights")
	private String skillIdWeights;

	@TableField(value = "ability_ids")
	private String abilityIds;

	@TableField(value = "knowledge_courses")
	private String knowledgeCourses;

	@TableField(value = "skill_courses")
	private String skillCourses;

}
