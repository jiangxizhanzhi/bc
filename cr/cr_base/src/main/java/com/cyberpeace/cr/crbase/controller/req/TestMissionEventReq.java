package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TestMissionEventReq implements Serializable {

	@ApiModelProperty(value = "房间id", required = true)
	private String roomId;

	@ApiModelProperty(value = "事件id", required = true)
	private Integer eventTypeId;

	@ApiModelProperty(value = "靶标id", required = true)
	private List<String> deviceIds;
}
