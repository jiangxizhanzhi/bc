package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvaTemplateAddReq {

	@ApiModelProperty(value = "测评靶场模板名称", required = true)
	@NotNull
	private String name;

	@ApiModelProperty(value = "测评靶场描述", required = false)
	private String description;

	@ApiModelProperty(value = "测评靶场Logo文件id", required = false)
	@NotNull
	private String logoId;

	@ApiModelProperty(value = "测评靶场类型", required = true)
	@NotNull
	private Integer categoryCode;

	@ApiModelProperty(value = "是否录屏", required = true)
	@NotNull
	private Boolean enableRecording;

	@ApiModelProperty(value = "拓扑配置", required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

	@ApiModelProperty(value = "步骤配置", required = true)
	@NotNull
	private List<EvalTaskStepConfig> stepConfig;

}