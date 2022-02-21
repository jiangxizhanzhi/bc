package com.cyberpeace.cr.hld.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HldRoomIdRsp implements Serializable {

	@ApiModelProperty("房间id")
	private String roomId;

}
