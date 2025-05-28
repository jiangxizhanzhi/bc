package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间战法攻击
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseTable
 * @since 1.0.0
 */
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

	private String strategy;

	private Boolean builtin;
}
