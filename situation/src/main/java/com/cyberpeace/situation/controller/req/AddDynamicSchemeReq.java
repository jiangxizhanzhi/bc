package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增动态方案请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AddDynamicSchemeReq implements Serializable {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景id")
	@NotBlank
	private String sceneId;

	/**
	 * 动效方案名称
	 *
	 * @see String
	 */
	@ApiModelProperty("动效方案名称")
	@NotBlank(message = SituationErrorCode.EMPTY_SCHEME_NAME)
	private String name;

	public AddDynamicSchemeReq(String sceneId, String name) {
		this.sceneId = sceneId;
		this.name = name;
	}
}
