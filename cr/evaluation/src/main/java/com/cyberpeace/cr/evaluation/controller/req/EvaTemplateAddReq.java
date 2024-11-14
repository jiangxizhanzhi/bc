package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估模板新增请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvaTemplateAddReq extends EvaTemplateBaseInfoReq {

	/**
	 * 拓扑配置
	 */
	@ApiModelProperty(value = "拓扑配置", required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

	/**
	 * 步骤配置
	 */
	@ApiModelProperty(value = "步骤配置", required = true)
	@NotNull
	private List<EvalTaskStepConfig> stepConfig;

	/**
	 * 授权用户
	 */
	@ApiModelProperty("授权用户")
	private List<String> authUserIds;

	/**
	 * 授权模式, 1-所有, 2-自定义
	 */
	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	private Integer operationAuthType;

}