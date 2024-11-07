package com.cyberpeace.cr.redvsblue.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * RB 设备
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@TableName("cr_rb_device")
public class RbDevice extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 装置id
	 */
	private String deviceId;

	/**
	 * 创造用户id
	 */
	private String createUserId;
}
