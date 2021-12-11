package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class WsSceneReportBO implements Serializable {

	private String code;

	private WsSceneReportData data;

	public WsSceneReportBO(String code, String type, JSONObject data) {
		WsSceneReportData reportData = new WsSceneReportData();
		reportData.setData(data);
		reportData.setType(type);

		this.setCode(code);
		this.setData(reportData);

	}

}

@Data
class WsSceneReportData implements Serializable {

	private String type;

	private JSONObject data;
}