package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("rtks_role_skill_relation")
public class RoleSkillRelation {

	@TableId(type = IdType.INPUT, value = "id")
	private Integer id;

	private String roleId;

	private String skillId;

	private Integer weight;
}
