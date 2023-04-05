package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.constant.ws.MessageType;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DashboardChangeReq implements Serializable {

	@ApiModelProperty("大屏ID")
	@NotEmpty
	private String dashboardId;

	@ApiModelProperty("特征ID")
	private String featureId;

	@ApiModelProperty("消息编码")
	private String code = MessageType.ChangePara.getDesc();

	@ApiModelProperty("消息内容")
	@NotEmpty
	private JSONObject data;
}
