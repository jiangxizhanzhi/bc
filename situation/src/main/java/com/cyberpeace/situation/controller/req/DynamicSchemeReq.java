package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.model.DynamicStage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动效方案请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class DynamicSchemeReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "资源id", required = true)
	@NotBlank
	private String resourceId;

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景id")
	@NotBlank
	private String sceneId;

	/**
	 * 是否为实例场景
	 */
	@ApiModelProperty("是否为实例场景")
	private Boolean isInstance;

	/**
	 * 动效方案名称
	 */
	@ApiModelProperty("动效方案名称")
	@NotBlank
	private String name;

	/**
	 * 动效方案数据
	 */
	@ApiModelProperty("动效方案数据")
	private List<DynamicStage> schemeData;
}
