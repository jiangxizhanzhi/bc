package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskRsp implements Serializable {

	@ApiModelProperty("测评靶场模板Id")
	private String templateId;

	@ApiModelProperty("测评名称")
	private String templateName;

	@ApiModelProperty("测评任务Id")
	private String taskId;

	@ApiModelProperty("测评任务名称")
	private String taskName;

	@ApiModelProperty("场景管理器id")
	private String localSceneId;

	@ApiModelProperty("场景id")
	private String mpSceneId;

	@ApiModelProperty("拓扑id")
	private String topoId;

	@ApiModelProperty("拓扑模板数据")
	private JSONObject topoData;

	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

	@ApiModelProperty("创建者（白方）")
	private String createUserId;

}
