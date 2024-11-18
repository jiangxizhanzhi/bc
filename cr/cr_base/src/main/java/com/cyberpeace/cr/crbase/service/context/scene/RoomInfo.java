package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 训练实例简要信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RoomInfo implements Serializable {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	private String roomId;

	/**
	 * 房间名称
	 *
	 * @see String
	 */
	private String roomName;

	public RoomInfo(String roomId, String roomName) {
		this.roomId = roomId;
		this.roomName = roomName;
	}
}
