package com.cyberpeace.oj.ojbase.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景实操请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class SceneOperationReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时、习题编号")
	@NotBlank
	private String resourceId;

}
