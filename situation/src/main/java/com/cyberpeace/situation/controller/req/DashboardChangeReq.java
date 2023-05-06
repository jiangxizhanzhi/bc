package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.constant.ws.MessageType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大屏基础请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class DashboardChangeReq implements Serializable {

	/**
	 * 大屏ID
	 */
	@ApiModelProperty("大屏ID")
	@NotEmpty
	private String dashboardId;

	/**
	 * 特性ID
	 */
	@ApiModelProperty("特征ID")
	private String featureId;

	/**
	 * 消息编码
	 */
	@ApiModelProperty("消息编码")
	private String code = MessageType.ChangePara.getDesc();

	/**
	 * 消息内容
	 */
	@ApiModelProperty("消息内容")
	@NotEmpty
	private JSONObject data;
}
