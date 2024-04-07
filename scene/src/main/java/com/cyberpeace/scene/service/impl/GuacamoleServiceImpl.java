package com.cyberpeace.scene.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.scene.dao.entity.NodeEntity;
import com.cyberpeace.scene.service.CpEnvService;
import com.cyberpeace.scene.service.GuacamoleService;
import com.cyberpeace.scene.service.bo.interactive.Access;
import com.cyberpeace.scene.service.bo.interactive.ConnectionReq;
import com.cyberpeace.scene.service.bo.interactive.ConnectionRes;
import com.cyberpeace.scene.service.bo.interactive.UserConnectionRes;
import com.cyberpeace.scene.util.HttpUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GuacamoleServiceImpl implements GuacamoleService {

	/**
	 * 云平台地址
	 */
	private String CP_HOST;

	/**
	 * 云平台实例地址
	 */
	private String CONNECTION_PATH = "/v1/guacamole/connection";

	private String USER_CONNECTION = "/v1/guacamole/connection?connection_id=";

	@Autowired
	private CpEnvService cpEnvService;

	@PostConstruct
	public void init() {
		CP_HOST = cpEnvService.getCpHost();
	}

	@Override
	public ConnectionRes createConnection(NodeEntity node) {
		Access access = node.getAccessModes().get(0);
		ConnectionReq req = new ConnectionReq(access.getUsername(), access.getPassword(),
				access.getHost(), access.getHostPort());
		log.info("start create connection req : {}", JsonUtil.toJsonString(req));
		ConnectionRes res = HttpUtil.post(CP_HOST + CONNECTION_PATH, req, ConnectionRes.class);
		log.info("end create connection end res : {}", JsonUtil.toJsonString(res));
		return res;
	}

	@Override
	public UserConnectionRes getUserLink(String connectionId) {
		log.info("start get user link with connection_id : {}", connectionId);
		UserConnectionRes res = HttpUtil.rawGet(CP_HOST + USER_CONNECTION + connectionId,
				UserConnectionRes.class);
		log.info("get user link with connection_id : {}| {}", connectionId, res.getUrl());
		return res;
	}

	@Override
	public void closeConnection(String connectionId) {
		log.info("start close connection, id : {}", connectionId);
		JSONObject body = new JSONObject();
		body.put("connection_id", connectionId);
		HttpUtil.delete(CP_HOST + USER_CONNECTION, body, JSONObject.class);
	}
}
