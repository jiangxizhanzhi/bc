package com.cyberpeace.situation.controller.req;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventRefreshReq {

	@ApiModelProperty(value = "大屏ID", required = true)
	private String dashboardId;

	@ApiModelProperty(value = "特征ID", required = true)
	private String featureId;

	@ApiModelProperty(value = "分靶场ID", required = false)
	private String subRangeId;

	@ApiModelProperty(value = "模式 1-实时 2-回放", required = true)
	private Integer mode;

	@ApiModelProperty(value = "大屏角色", required = true)
	private String role;

	@ApiModelProperty(value = "大屏当前天文时间", required = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date currentTime;

}
