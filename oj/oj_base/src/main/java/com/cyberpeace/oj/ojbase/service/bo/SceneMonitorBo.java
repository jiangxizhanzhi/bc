package com.cyberpeace.oj.ojbase.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景监控博
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class SceneMonitorBo implements Serializable {

	/**
	 * 用户id
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 监视器信息
	 */
	@ApiModelProperty("监控信息")
	private List<MonitorInfoBo> monitorInfo;

}
