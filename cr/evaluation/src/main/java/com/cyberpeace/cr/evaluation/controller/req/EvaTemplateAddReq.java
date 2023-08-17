package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EvaTemplateAddReq extends EvaTemplateBaseInfoReq {

	@ApiModelProperty(value = "拓扑配置", required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

	@ApiModelProperty(value = "步骤配置", required = true)
	@NotNull
	private List<EvalTaskStepConfig> stepConfig;

	@ApiModelProperty("授权人编号集合")
	private String authIds;

	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	@NotNull
	private Integer authType;

}