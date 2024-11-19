package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 岗位技能关系
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
@TableName("rtks_role_skill_relation")
public class RoleSkillRelation {

	/**
	 * id
	 *
	 * @see Integer
	 */
	@TableId(type = IdType.INPUT, value = "id")
	private Integer id;

	/**
	 * 岗位id
	 *
	 * @see String
	 */
	private String roleId;

	/**
	 * 技能id
	 *
	 * @see String
	 */
	private String skillId;

	/**
	 * 重量
	 *
	 * @see Integer
	 */
	private Integer weight;
}
