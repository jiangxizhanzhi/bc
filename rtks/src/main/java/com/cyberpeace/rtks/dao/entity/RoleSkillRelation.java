package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 角色技能关系
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@TableName("rtks_role_skill_relation")
public class RoleSkillRelation {

	/**
	 * id
	 */
	@TableId(type = IdType.INPUT, value = "id")
	private Integer id;

	/**
	 * 角色id
	 */
	private String roleId;

	/**
	 * 技能id
	 */
	private String skillId;

	/**
	 * 重量
	 */
	private Integer weight;
}
