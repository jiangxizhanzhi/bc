package com.cyberpeace.cs.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.cs.service.WebRTCService;
import com.cyberpeace.cs.service.context.rtc.WebRTCReq;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebRTCServiceImpl implements WebRTCService {

	private String PUBLISH_URL;

	private String PLAY_URL;

	@PostConstruct
	public void init() {
		String srsHost = SystemPropUtil.getProp("SRS_HOST");
		String publishUrl = ConfigUtil.getContextProperty("rtc.publish.url");
		String playUrl = ConfigUtil.getContextProperty("rtc.play.url");

		PUBLISH_URL = srsHost + publishUrl;
		PLAY_URL = srsHost + playUrl;
	}

	@Override
	public String getPublishUrl(WebRTCReq req) {
		return PUBLISH_URL + req.getRoomId();
	}

	@Override
	public String getPlayUrl(WebRTCReq req) {
		return PLAY_URL + req.getRoomId();
	}
}
