package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 聘更新请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EngageUpdateReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("Engage id")
	@NotNull
	private String resourceId;

	/**
	 * 业务模板id列表
	 */
	@ApiModelProperty("场景模板id")
	@NotNull
	private List<String> businessTemplateIds;
}