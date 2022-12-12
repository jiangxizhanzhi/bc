package com.cyberpeace.situation.service.impl;

import org.springframework.stereotype.Component;

import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.situation.model.ws.DashboardWsMessageIdentifier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SituationWebSocketHandler extends MyWebSocketHandler {

	private final String AND = "&";

	public <T> void pushMessageToDashboard(
			WebSocketMessageContext<DashboardWsMessageIdentifier, T> message) {
		String dashboardId = message.getMessageIdentifier().getDashboardId();
		String path = message.getMessageIdentifier().getPath();
		businessSocketSessionMap.forEach((key, value) -> {
			if (match(key, dashboardId, path)) {
				value.forEach((userId, sessionSet) -> {
					pushToSessions(message, sessionSet, userId, dashboardId + AND + path);
				});
			}
		});
	}

	private Boolean match(String key, String dashboardId, String path) {
		if (key.contains(path + AND) || key.contains(AND + path)) {
			return key.contains(dashboardId + AND) || key.contains(AND + dashboardId);
		}
		return false;
	}
}
