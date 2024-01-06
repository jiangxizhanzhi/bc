package com.cyberpeace.scene.service.bo;

import lombok.Data;

@Data
public class WsSceneReportCodeData {

	private String code;

	private WsData data;

	public WsSceneReportCodeData(String code, WsData wsData) {
		this.code = code;
		this.data = wsData;
	}
}
