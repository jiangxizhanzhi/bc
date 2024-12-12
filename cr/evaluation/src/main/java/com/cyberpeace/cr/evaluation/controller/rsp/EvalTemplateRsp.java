package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估模板响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTemplateRsp extends EvalTemplateListRsp implements Serializable {

	/**
	 * 启用录制
	 */
	@ApiModelProperty(value = "是否录屏")
	private Boolean enableRecording;

	/**
	 * 场景配置数据
	 */
	@ApiModelProperty(value = "拓扑配置")
	private JSONObject sceneConfigData;

	/**
	 * 步骤配置
	 */
	@ApiModelProperty(value = "步骤配置", required = true)
	private List<EvalTaskStepConfig> stepConfig;

	/**
	 * 当前任务
	 */
	@ApiModelProperty(value = "当前测评任务")
	private String currentTask;

	public void setStepConfig(String stepConfig) {
		this.stepConfig = JsonUtil.toJsonObjectArray(stepConfig, EvalTaskStepConfig.class);
	}

}
