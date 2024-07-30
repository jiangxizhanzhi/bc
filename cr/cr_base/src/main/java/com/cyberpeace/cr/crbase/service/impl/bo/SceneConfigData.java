package com.cyberpeace.cr.crbase.service.impl.bo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景配置数据
 */
@Data
public class SceneConfigData implements Serializable {

	@ApiModelProperty(value = "拓扑配置", required = true)
	@NotNull
	private JSONObject sceneConfig;

	@ApiModelProperty(value = "拓扑LOGO")
	private String logo;

	@ApiModelProperty(value = "是否只是部分启动")
	private Boolean supportPartialStart;

	@ApiModelProperty(value = "场景拓扑id")
	private String id ;
}
