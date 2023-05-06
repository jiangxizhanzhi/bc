package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大屏实例发布请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationInstanceReleaseReq implements Serializable {

	/**
	 * 大屏实例id
	 */
	@ApiModelProperty(value = "态势实例id", required = true)
	@NotBlank
	private String resourceId;

	/**
	 * 类型
	 */
	private String type;
}
