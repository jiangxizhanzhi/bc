package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 训练实例简要信息
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class RoomInfo implements Serializable {

	private String roomId;

	private String roomName;

	public RoomInfo(String roomId, String roomName) {
		this.roomId = roomId;
		this.roomName = roomName;
	}
}
