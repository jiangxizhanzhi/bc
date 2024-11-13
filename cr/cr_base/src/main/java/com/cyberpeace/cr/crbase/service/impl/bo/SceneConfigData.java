package com.cyberpeace.cr.crbase.service.impl.bo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景配置数据
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SceneConfigData implements Serializable {

	/**
	 * 拓扑配置
	 */
	@ApiModelProperty(value = "拓扑配置", required = true)
	@NotNull
	private JSONObject sceneConfig;

	/**
	 * 拓扑LOGO
	 */
	@ApiModelProperty(value = "拓扑LOGO")
	private String logo;

	/**
	 * 是否支持部分启动
	 */
	@ApiModelProperty(value = "是否支持部分启动")
	private Boolean supportPartialStart;

	/**
	 * 场景拓扑id
	 */
	@ApiModelProperty(value = "场景拓扑id")
	private String id ;
}
