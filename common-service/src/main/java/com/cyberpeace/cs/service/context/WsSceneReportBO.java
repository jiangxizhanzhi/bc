package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WS场景报告博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class WsSceneReportBO implements Serializable {

	/**
	 * 代号
	 *
	 * @see String
	 */
	private String code;

	/**
	 * 数据
	 *
	 * @see WsSceneReportData
	 */
	private WsSceneReportData data;

	public WsSceneReportBO(String code, String type, JSONObject data) {
		WsSceneReportData reportData = new WsSceneReportData();
		reportData.setData(data);
		reportData.setType(type);

		this.setCode(code);
		this.setData(reportData);
	}
}