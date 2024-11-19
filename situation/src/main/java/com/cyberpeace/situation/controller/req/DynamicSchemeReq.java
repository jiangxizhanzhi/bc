package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.model.DynamicStage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动态方案请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class DynamicSchemeReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源id", required = true)
	@NotBlank
	private String resourceId;

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景id")
	@NotBlank
	private String sceneId;

	/**
	 * 是否为实例场景
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否为实例场景")
	private Boolean isInstance;

	/**
	 * 动效方案名称
	 *
	 * @see String
	 */
	@ApiModelProperty("动效方案名称")
	@NotBlank
	private String name;

	/**
	 * 动效方案数据
	 *
	 * @see List<DynamicStage>
	 */
	@ApiModelProperty("动效方案数据")
	private List<DynamicStage> schemeData;
}
