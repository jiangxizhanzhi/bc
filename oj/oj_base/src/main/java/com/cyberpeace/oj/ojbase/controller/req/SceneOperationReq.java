package com.cyberpeace.oj.ojbase.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景实操请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class SceneOperationReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("课时、习题编号")
	@NotBlank
	private String resourceId;

}
