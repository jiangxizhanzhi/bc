package com.cyberpeace.cr.evaluation.controller.req;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalCloudTemplateAddReq implements Serializable {

	@ApiModelProperty("云模板id")
	private String cloudTemplateId;

	@ApiModelProperty("基础信息")
	private EvalCloudTemplateBaseReq baseInfo;

	@ApiModelProperty("场景配置数据")
	private JSONObject sceneConfigData;

	@ApiModelProperty("试验步骤id列表")
	private List<String> stepIds;

}
