package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增动效方案请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
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

	/**
	 * 构造函数
	 *
	 * @param sceneId 场景id
	 * @param name    动效方案名称
	 */
	public AddDynamicSchemeReq(String sceneId, String name) {
		this.sceneId = sceneId;
		this.name = name;
	}
}
