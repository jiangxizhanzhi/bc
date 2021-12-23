package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LessonMonitorBo implements Serializable {

	@ApiModelProperty("用户编号")
	private String userId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("监控信息")
	private List<MonitorInfoBo> monitorInfo;

}
