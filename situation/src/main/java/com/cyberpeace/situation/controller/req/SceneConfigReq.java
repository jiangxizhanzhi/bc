package com.cyberpeace.situation.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.situation.service.context.SceneConfigContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景配置请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SceneConfigReq extends SceneConfigContext {

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景id")
	@NotNull
	private String sceneId;

	/**
	 * 是否为实例场景
	 */
	@ApiModelProperty("是否为实例场景")
	private Boolean isInstance;

}