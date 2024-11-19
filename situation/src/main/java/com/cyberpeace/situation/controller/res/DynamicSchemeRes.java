package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.model.DynamicStage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动态方案结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class DynamicSchemeRes implements Serializable {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景id")
	private String sceneId;

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
