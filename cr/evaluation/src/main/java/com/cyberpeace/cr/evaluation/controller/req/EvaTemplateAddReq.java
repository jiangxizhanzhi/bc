package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvaTemplateAddReq extends EvaTemplateBaseInfoReq {

	@ApiModelProperty(value = "拓扑配置", required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

	@ApiModelProperty(value = "步骤配置", required = true)
	@NotNull
	private List<EvalTaskStepConfig> stepConfig;

	@ApiModelProperty("授权用户")
	private List<String> authUserIds;

	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	private Integer operationAuthType;

	@ApiModelProperty("战法和模板的数据隔离：1是模板,0是战法")
	private Integer isTem;

}