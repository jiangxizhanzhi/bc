package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * WS场景报告博
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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