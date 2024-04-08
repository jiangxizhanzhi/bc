package com.cyberpeace.scene.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.scene.service.bo.interactive.QQRes;

public interface PineappleService {
    List<QQRes> listQQ();

	String activateDWall();

	JSONObject getClients();
}
