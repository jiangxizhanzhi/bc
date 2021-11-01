package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.AuthTable;

import lombok.Data;

@Data
@TableName("cr_business_template")
public class BusinessTemplate extends AuthTable {

	// TODO
	// businessType的常量定义

	private String topoId;

	private String name;

	private String description;

	private Integer businessType;

	private Integer difficulty;

	private String createUserId;
}
