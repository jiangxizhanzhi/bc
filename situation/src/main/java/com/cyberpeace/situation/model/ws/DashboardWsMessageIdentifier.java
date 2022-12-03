package com.cyberpeace.situation.model.ws;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

@Data
public class DashboardWsMessageIdentifier extends WsMessageIdentifier {

	private String dashboardId;

	private String featureId;

	public void setSceneId(String dashboardId, String featureId) {
		this.dashboardId = dashboardId;
		this.featureId = featureId;
	}

}
