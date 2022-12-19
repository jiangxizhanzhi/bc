package com.cyberpeace.cr.crbase.controller.req;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TopoDataReq implements Serializable {

	@ApiModelProperty(value = "资源id", required = true)
	private String resourceId;

	@ApiModelProperty(value = "名称", required = true)
	private String name;

	@ApiModelProperty(value = "场景配置", required = true)
	private JSONObject sceneConfig;
}
