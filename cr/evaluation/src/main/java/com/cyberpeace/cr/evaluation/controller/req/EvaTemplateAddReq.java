package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 伊娃模板新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see EvaTemplateBaseInfoReq
 * @since 1.0.0
 */
@Data
public class EvaTemplateAddReq extends EvaTemplateBaseInfoReq {

	/**
	 * 场景配置数据
	 *
	 * @see SceneConfigData
	 */
	@ApiModelProperty(value = "拓扑配置", required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

	/**
	 * 步骤配置
	 *
	 * @see List<EvalTaskStepConfig>
	 */
	@ApiModelProperty(value = "步骤配置", required = true)
	@NotNull
	private List<EvalTaskStepConfig> stepConfig;

}