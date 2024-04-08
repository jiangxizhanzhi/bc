package com.cyberpeace.cr.evaluation.controller.rsp;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalTestRsp implements Serializable {

	@ApiModelProperty("试验id")
	private String testId;

	@ApiModelProperty("试验名称")
	private String testName;

	@ApiModelProperty("试验实例id")
	private String instanceId;

	@ApiModelProperty("试验实例名称")
	private String instanceName;

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
}
