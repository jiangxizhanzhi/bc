package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.model.DynamicStage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动效方案返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class DynamicSchemeRes implements Serializable {

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景id")
	private String sceneId;

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
