package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.AuthTable;

import lombok.Data;

/**
 * 靶场模板公共信息模型
 *  仅使用 operationAuthType， operationAuthIds
 *  不使用 readAuthType， readAuthIds
 * */
@Data
@TableName("cr_business_template")
public class BusinessTemplate extends AuthTable {

	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	private String topoId;

	private String name;

	private String description;

	private Integer businessType;

	private Integer difficulty;

	private String createUserId;

	private String logo;

	/**
	 * 用于关联后台测试场景
	 */
	private String localSceneId;

	@TableField(exist = false)
	private Integer nodeCount;
	@TableField(exist = false)
	private String createUser;
	@TableField(exist = false)
	private String createTimeStr;


}
