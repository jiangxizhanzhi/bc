package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("cr_team_train_template_role")
public class BaseRole {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**角色名称
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private Integer roleGroup;


}