package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
 * WS场景报告数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class WsSceneReportData implements Serializable {

	/**
	 * 类型
	 *
	 * @see String
	 */
	private String type;

	/**
	 * 数据
	 *
	 * @see JSONObject
	 */
	private JSONObject data;
}
