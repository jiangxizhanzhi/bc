package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CrSceneRetrieveRsp implements Serializable {

	@ApiModelProperty("房间id")
	private String roomId;

	@ApiModelProperty("场景管理器id")
	private String sceneManagerId;

	@ApiModelProperty("拓扑模板睡数据")
	private JSONObject topoData;

	@ApiModelProperty("场景数据")
	private JSONObject sceneData;
}
