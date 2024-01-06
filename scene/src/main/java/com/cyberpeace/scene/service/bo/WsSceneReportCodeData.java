package com.cyberpeace.scene.service.bo;

import lombok.Data;

@Data
public class WsSceneReportCodeData {

	private Integer code;

	private WsData data;

	public WsSceneReportCodeData(Integer code, WsData wsData) {
		this.code = code;
		this.data = wsData;
	}
}
