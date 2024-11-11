package com.cyberpeace.oj.practice.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景共享模式请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class SceneShareModeReq implements Serializable {

	/**
	 * 场景共享模式
	 */
	@ApiModelProperty("隔离模式设置 1=个人 2=共享")
	@NotNull
	private Integer sceneShareMode;

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	@NotBlank
	private String resourceId;

}
