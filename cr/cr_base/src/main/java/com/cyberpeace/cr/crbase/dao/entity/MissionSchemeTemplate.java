package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务计划模板
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_mission_scheme_template")
public class MissionSchemeTemplate extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 创造用户id
	 */
	private String createUserId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 说明：http://10.10.10.11/pages/viewpage.action?pageId=34113495
	 */
	private String templateData;

	/**
	 * 说明： http://10.10.10.11/pages/viewpage.action?pageId=38209993
	 */
	private String templateConfig;

}
