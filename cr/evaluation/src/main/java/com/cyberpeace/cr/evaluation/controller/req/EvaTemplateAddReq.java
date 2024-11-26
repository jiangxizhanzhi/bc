package com.cyberpeace.cr.evaluation.controller.req;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;

import com.cyberpeace.cr.evaluation.service.impl.bo.MissionScheme;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvaTemplateAddReq extends EvaTemplateBaseInfoReq {

	@ApiModelProperty(value = "拓扑配置", required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

	@ApiModelProperty(value = "步骤配置", required = true)
	private List<EvalTaskStepConfig> stepConfig = new ArrayList<>();

	@ApiModelProperty("授权用户")
	private List<String> authUserIds;

	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	private Integer operationAuthType;

	@ApiModelProperty("试验阶段-技战法")
	private MissionScheme missionsScheme;

	@ApiModelProperty("模板id")
	private String templateId;

	@ApiModelProperty("体系ID")
	private String systemId;
}