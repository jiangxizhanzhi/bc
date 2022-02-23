package com.cyberpeace.cr.hld.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HldRoomIdRsp implements Serializable {

	@ApiModelProperty("房间id")
	private String roomId;

	public HldRoomIdRsp(String roomId) {
		this.roomId = roomId;
	}

}
