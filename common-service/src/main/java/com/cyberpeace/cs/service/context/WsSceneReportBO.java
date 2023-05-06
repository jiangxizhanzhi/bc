package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
 * ws场景报告
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class WsSceneReportBO implements Serializable {

	private String code;

	private WsSceneReportData data;

	/**
	 * ws场景报告
	 *
	 * @param code 代码
	 * @param type 类型
	 * @param data 数据
	 */
	public WsSceneReportBO(String code, String type, JSONObject data) {
		WsSceneReportData reportData = new WsSceneReportData();
		reportData.setData(data);
		reportData.setType(type);

		this.setCode(code);
		this.setData(reportData);
	}
}