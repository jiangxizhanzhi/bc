package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ExportDynamicSchemeReq implements Serializable {

	/**
	 * 动效方案id
	 */
    @ApiModelProperty(value = "动效方案id",required = true)
    @NotBlank
    private String resourceId;
}
