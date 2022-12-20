package com.cyberpeace.integratation.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SchemeRspBo implements Serializable {

	@ApiModelProperty(value = "可视化方案id")
	private String schemeId;

	@ApiModelProperty(value = "可视化方案名称")
	private String schemeName;
}
