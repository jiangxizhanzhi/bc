package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import lombok.Data;

/**
 * 训练角色模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RoomRoleContext implements Serializable {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	private String roomId;

	/**
	 * 角色组
	 *
	 * @see Integer
	 */
	private Integer roleGroup;
}
