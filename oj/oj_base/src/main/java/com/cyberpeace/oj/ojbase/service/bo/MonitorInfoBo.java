package com.cyberpeace.oj.ojbase.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 监控信息 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MonitorInfoBo implements Serializable {

	/**
	 * 服务器
	 *
	 * @see String
	 */
	@ApiModelProperty("节点名称")
	private String server;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 监控会话
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("会话信息")
	private JSONObject monitorSessions;

}
