package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("space_tactic_defense_category")
public class SpaceTacticDefenseCategory extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID)
	private String resourceId;

	private String name;

	private Integer stage;

	private String cover;

}
