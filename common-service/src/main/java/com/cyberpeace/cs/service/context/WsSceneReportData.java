package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
 * WS场景报告数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class WsSceneReportData implements Serializable {

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 数据
	 */
	private JSONObject data;
}
