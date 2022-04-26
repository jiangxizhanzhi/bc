package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("rtks_role_skill_relation")
public class RoleSkillRelation {

	private Integer id;

	private String roleId;

	private String skillId;

	private Integer weight;
}
