package com.cyberpeace.cr.hld.controller.rsp;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.service.context.SceneViewContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HldRoomRsp extends SceneViewContext implements Serializable {

	@ApiModelProperty("房间状态 0-已结束 1-已暂停 2-已开始 3-恢复 4-未开始")
	private Integer roomStatus;

	@ApiModelProperty("业务模板id, 测试场景用")
	private String templateId;

	@ApiModelProperty("任务名称")
	private String taskName;

}
