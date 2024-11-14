package com.cyberpeace.oj.ojbase.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景监控Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SceneMonitorBo implements Serializable {

	/**
	 * 用户编号
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 监控信息
	 */
	@ApiModelProperty("监控信息")
	private List<MonitorInfoBo> monitorInfo;

}
