package com.cyberpeace.cr.evaluation.controller.rsp;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.evaluation.service.impl.bo.BaseTemplateData;
import com.cyberpeace.cr.evaluation.service.impl.bo.MissionScheme;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EvalTemTemplateRsp implements Serializable {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty("基础数据")
	@NotNull
	private BaseTemplateData baseData;

	@ApiModelProperty("场景拓扑")
	private JSONObject sceneConfigData;

	@ApiModelProperty("任务方案")
	private MissionScheme missionsScheme;
}
