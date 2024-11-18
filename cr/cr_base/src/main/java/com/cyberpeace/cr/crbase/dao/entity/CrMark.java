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
 * @version 1.0.0
 * @date 2023/05/24
 * @see BaseTable
 * @since 1.0.0
 */
@TableName(value = "cr_mark")
@Data
public class CrMark extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 证据
	 *
	 * @see String
	 */
	private String evidence;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	private String roomId;

	/**
	 * 训练类型
	 *
	 * @see Integer
	 */
	private Integer type;
}
