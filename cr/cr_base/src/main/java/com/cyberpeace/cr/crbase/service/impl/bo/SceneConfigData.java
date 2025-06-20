package com.cyberpeace.cr.crbase.service.impl.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 场景配置数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SceneConfigData implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 场景配置
	 */
	@ApiModelProperty(value = "拓扑配置", required = true)
	@NotNull
	private JSONObject sceneConfig;

	/**
	 * 图标
	 */
	@ApiModelProperty(value = "拓扑LOGO")
	private String logo;

	/**
	 * 支持 Partial Start
	 */
	@ApiModelProperty(value = "是否只是部分启动")
	private Boolean supportPartialStart;
}
