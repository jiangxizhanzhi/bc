package com.cyberpeace.cr.hld.controller.rsp;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HldTopoRsp implements Serializable {

	@ApiModelProperty("HLD拓扑id")
	private String resourceId;

	@ApiModelProperty("拓扑状态 0-已结束 1-已暂停 2-已开始 3-恢复 4-未开始")
	private Integer status;

	@ApiModelProperty("任务名称")
	private String taskName;

	@ApiModelProperty("场景id")
	private String mpSceneId;

	@ApiModelProperty("拓扑模板数据")
	private JSONObject topoData;

	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

	@JsonIgnore
	public void setRoomId(String resourceId){
		this.resourceId = resourceId;
	}
}

