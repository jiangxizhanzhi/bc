package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.AuthTable;

import lombok.Data;

/**
 * 靶场模板公共信息模型
 * */
@Data
@TableName("cr_business_template")
public class BusinessTemplate extends AuthTable {

	// TODO
	// businessType的常量定义
	private String resourceId;

	private String topoId;

	private String name;

	private String description;

	private Integer businessType;

	private Integer difficulty;

	private String createUserId;

	public interface CeType {

		int TRAINING_SINGLE = 1;

		int VULN_VALIDATE = 2;

		int TRAINING_TEAM = 3;

		int RED_VS_BLUE = 4;

		int WEAPON_ASSESS = 6;

		int SYSTEM_ASSESS = 7;
	}
}
