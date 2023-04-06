package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

@Data
public class PlaybackReq implements Serializable {

	@ApiParam(name = "scene_id", value = "场景编号", required = true)
	private String sceneId;

	@ApiParam(value = "开始时间")
	private String startTime;

	@ApiParam(value = "截止时间")
	private String endTime;
}
