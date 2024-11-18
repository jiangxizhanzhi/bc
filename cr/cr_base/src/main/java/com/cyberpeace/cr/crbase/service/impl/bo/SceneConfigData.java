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
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneConfigData implements Serializable {

	/**
	 * 场景配置
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty(value = "拓扑配置", required = true)
	@NotNull
	private JSONObject sceneConfig;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "拓扑LOGO")
	private String logo;

	/**
	 * 支持 Partial Start
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否只是部分启动")
	private Boolean supportPartialStart;
}
