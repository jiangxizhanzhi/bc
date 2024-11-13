package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * cr标记
 *
 * @author SN
 * @date 2023/05/24
 */
@TableName(value = "cr_mark")
@Data
public class CrMark extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 证据
	 */
	private String evidence;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 训练类型
	 */
	private Integer type;
}
