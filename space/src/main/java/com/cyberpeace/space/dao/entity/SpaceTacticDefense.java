package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("space_tactic_defense")
public class SpaceTacticDefense extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID)
	private String resourceId;

	private String name;

	private String tacticId;

	private String categoryId;

	private Integer role;

	private String tag;

	private String description;

	private String scene;

	private String monitor;

	@TableField("`analyze`")
	private String analyze;

	private String tool;
}
