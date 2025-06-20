package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 训练实例简要信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class RoomInfo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
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
