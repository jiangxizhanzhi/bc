package com.cyberpeace.cr.hld.controller.rsp;

import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HldRoomRsp extends SceneViewContext implements Serializable {

	@ApiModelProperty("房间状态 0-已结束 1-已暂停 2-已开始 3-恢复 4-未开始")
	private Integer roomStatus;

}
