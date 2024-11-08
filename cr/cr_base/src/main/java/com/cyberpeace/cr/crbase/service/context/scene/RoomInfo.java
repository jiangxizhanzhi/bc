package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 训练实例简要信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class RoomInfo implements Serializable {

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 房间名称
	 */
	private String roomName;

	public RoomInfo(String roomId, String roomName) {
		this.roomId = roomId;
		this.roomName = roomName;
	}
}
