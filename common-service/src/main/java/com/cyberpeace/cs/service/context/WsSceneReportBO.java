package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WS场景报告Bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@NoArgsConstructor
public class WsSceneReportBO implements Serializable {

	/**
	 * 代号
	 */
	private String code;

	/**
	 * 数据
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