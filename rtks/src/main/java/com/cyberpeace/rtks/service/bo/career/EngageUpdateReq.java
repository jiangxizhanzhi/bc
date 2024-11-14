package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EngageUpdateReq implements Serializable {

	/**
	 * Engage id
	 */
	@ApiModelProperty("Engage id")
	@NotNull
	private String resourceId;

	/**
	 * 场景模板id
	 */
	@ApiModelProperty("场景模板id")
	@NotNull
	private List<String> businessTemplateIds;
}