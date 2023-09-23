package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.context.user.OperationUserInfo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTemplateRsp extends EvalTemplateListRsp {

	@ApiModelProperty(value = "是否录屏")
	private Boolean enableRecording;

	@ApiModelProperty(value = "拓扑配置")
	private JSONObject sceneConfigData;

	@ApiModelProperty(value = "步骤配置", required = true)
	private List<EvalTaskStepConfig> stepConfig;

	@ApiModelProperty(value = "当前测评任务")
	private String currentTask;

	@ApiModelProperty("授权用户")
	private List<OperationUserInfo> operationAuthIds;

	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	private Integer operationAuthType;

	public void setStepConfig(String stepConfig) {
		this.stepConfig = JsonUtil.toJsonObjectArray(stepConfig, EvalTaskStepConfig.class);
	}

}
