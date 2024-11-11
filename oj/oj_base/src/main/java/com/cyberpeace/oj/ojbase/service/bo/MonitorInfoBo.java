package com.cyberpeace.oj.ojbase.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 监控信息 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class MonitorInfoBo implements Serializable {

	/**
	 * 服务器
	 */
	@ApiModelProperty("节点名称")
	private String server;

	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 监控会话
	 */
	@ApiModelProperty("会话信息")
	private JSONObject monitorSessions;

}
