package com.cyberpeace.integratation.service.bo.situation;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TopoHistoryBo implements Serializable {

	@ApiModelProperty(value = "变更时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	@ApiModelProperty(value = "可视化方案ID")
	private String schemeId;

	@ApiModelProperty(value = "变更ID")
	private String updateId;
}
