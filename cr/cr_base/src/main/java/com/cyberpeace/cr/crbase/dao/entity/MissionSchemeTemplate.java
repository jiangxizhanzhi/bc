package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 任务计划模板
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("cr_mission_scheme_template")
public class MissionSchemeTemplate extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	private String title;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 说明：http://10.10.10.11/pages/viewpage.action?pageId=34113495
	 *
	 * @see String
	 */
	private String templateData;

	/**
	 * 说明： http://10.10.10.11/pages/viewpage.action?pageId=38209993
	 *
	 * @see String
	 */
	private String templateConfig;

}
