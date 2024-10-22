package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("space_tactic_attack")
public class SpaceTacticAttack extends BaseTable {

	@TableId(type = IdType.ASSIGN_ID)
	private String resourceId;

	private String name;

	private String tacticId;

	private String categoryId;

	private Integer role;

	private String tag;

	private String description;

	private String scene;

	private String feature;

	private String action;

	private String tool;
}
