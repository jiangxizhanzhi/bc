package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoomInfo implements Serializable {

	private String roomId;

	private String roomName;

	public RoomInfo(String roomId, String roomName) {
		this.roomId = roomId;
		this.roomName = roomName;
	}
}
