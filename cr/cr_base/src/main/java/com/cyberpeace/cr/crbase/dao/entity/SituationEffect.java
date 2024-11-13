package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 态势动效
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
@TableName("situation_effect")
public class SituationEffect extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 类别id
	 */
	private String categoryId;

	/**
	 * 文件
	 */
	private String file;
}
