package com.cyberpeace.integratation.service.bo.situation;

import java.io.Serializable;

import com.cyberpeace.integratation.model.situation.req.TaskBaseReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlatformReq extends TaskBaseReq implements Serializable {

	@ApiModelProperty(value = "可视化方案id")
	private String schemeId;

	@ApiModelProperty(value = "环境配置方案ID")
	private String envId;

	@ApiModelProperty("平台ID")
	private String platformId;

}
