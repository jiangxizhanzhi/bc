package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 图片请求
 *
 * @Author xuxz
 * @Date 31/1/2023 下午5:38
 */
@Data
public class ImageReq implements Serializable {

	/**
	 * 图片base64
	 */
	@ApiModelProperty("图片base64")
	@NotNull
	private String base64;
}
