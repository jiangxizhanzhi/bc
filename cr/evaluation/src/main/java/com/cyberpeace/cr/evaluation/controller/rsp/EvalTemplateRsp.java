package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估模板响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see EvalTemplateListRsp
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalTemplateRsp extends EvalTemplateListRsp implements Serializable {

	/**
	 * 启用录制
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否录屏")
	private Boolean enableRecording;

	/**
	 * 场景配置数据
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty(value = "拓扑配置")
	private JSONObject sceneConfigData;

	/**
	 * 步骤配置
	 *
	 * @see List<EvalTaskStepConfig>
	 */
	@ApiModelProperty(value = "步骤配置", required = true)
	private List<EvalTaskStepConfig> stepConfig;

	/**
	 * 当前任务
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "当前测评任务")
	private String currentTask;

	public void setStepConfig(String stepConfig) {
		this.stepConfig = JsonUtil.toJsonObjectArray(stepConfig, EvalTaskStepConfig.class);
	}

}
