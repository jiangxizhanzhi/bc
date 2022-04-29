package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTemplateRsp extends EvalTemplateListRsp implements Serializable {

	@ApiModelProperty(value = "是否录屏")
	private Boolean enableRecording;

	@ApiModelProperty(value = "拓扑配置")
	private SceneConfigData sceneConfigData;

	@ApiModelProperty(value = "步骤配置", required = true)
	private List<EvalTaskStepConfig> stepConfig;

	@ApiModelProperty(value = "任务列表")
	private List<String> taskList;

	@ApiModelProperty(value = "当前任务")
	private String currentTask;

}
