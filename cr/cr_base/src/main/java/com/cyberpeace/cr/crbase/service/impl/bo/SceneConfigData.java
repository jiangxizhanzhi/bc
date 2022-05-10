package com.cyberpeace.cr.crbase.service.impl.bo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneConfigData implements Serializable {

	@ApiModelProperty(required = true)
	@NotNull
	private JSONObject sceneConfig;

	private String logo;

}
