package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddDynamicSchemeReq implements Serializable {

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景id")
	@NotBlank
	private String sceneId;

	/**
	 * 动效方案名称
	 */
	@ApiModelProperty("动效方案名称")
	@NotBlank(message = SituationErrorCode.EMPTY_SCHEME_NAME)
	private String name;

	public AddDynamicSchemeReq(String sceneId, String name) {
		this.sceneId = sceneId;
		this.name = name;
	}
}
