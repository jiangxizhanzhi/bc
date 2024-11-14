package com.cyberpeace.oj.ojbase.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 监控信息Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class MonitorInfoBo implements Serializable {

	/**
	 * 节点名称
	 */
	@ApiModelProperty("节点名称")
	private String server;

	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 会话信息
	 */
	@ApiModelProperty("会话信息")
	private JSONObject monitorSessions;

}
