package com.cyberpeace.scene.service;

import com.cyberpeace.scene.dao.entity.NodeEntity;
import com.cyberpeace.scene.service.bo.interactive.ConnectionRes;
import com.cyberpeace.scene.service.bo.interactive.UserConnectionRes;

public interface GuacamoleService {

	ConnectionRes createConnection(NodeEntity node);

	UserConnectionRes getUserLink(String connectionId);

	void closeConnection(String connectionId);
}
