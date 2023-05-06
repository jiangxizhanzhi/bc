package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 任务计划模板
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@TableName("cr_mission_scheme_template")
public class MissionSchemeTemplate extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String createUserId;

	private String title;

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
