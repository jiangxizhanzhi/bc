package com.cyberpeace.scene.service;

import java.util.List;

import com.cyberpeace.scene.service.bo.interactive.PineappleClientRes;
import com.cyberpeace.scene.service.bo.interactive.QQRes;

public interface PineappleService {
    List<QQRes> listQQ();

	String activateDWall();

	PineappleClientRes getClients();

	void updateSsid(String ssid);

	String getSsid();
}
