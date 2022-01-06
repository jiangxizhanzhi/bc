package com.cyberpeace.oj.ojbase.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MonitorInfoBo implements Serializable {

	@ApiModelProperty("节点名称")
	private String server;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("会话信息")
	private JSONObject monitorSessions;

}
