package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("space_tactic_attack")
public class SpaceTacticAttack extends BaseTable {

	@TableId(type = IdType.ASSIGN_ID)
	private String resourceId;

	private String name;

	private String categoryId;

	private String source;

	private String systemId;

	private String action;

	private String tool;
}
