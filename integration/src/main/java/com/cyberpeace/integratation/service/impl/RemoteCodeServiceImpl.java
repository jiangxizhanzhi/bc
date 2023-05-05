package com.cyberpeace.integratation.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.situation.res.EventListRsp;
import com.cyberpeace.integratation.service.RemoteCodeService;

@Service
public class RemoteCodeServiceImpl implements RemoteCodeService {

	@Autowired
	private SituationEnvService situationEnvService;

	private String TS_GET_EVENT_LIST;

	@PostConstruct
	public void init() {
		String outerHostTs = situationEnvService.getTsHost();
		TS_GET_EVENT_LIST = outerHostTs + ConfigUtil.getContextProperty("event.list");
	}

	@Override
	public List<EventListRsp> getEvents() {
		String rsp = HttpClientUtil.tsGet(TS_GET_EVENT_LIST, String.class, "获取态势系统特定事件类型数据失败");
		return JsonUtil.toJsonObjectArray(rsp, EventListRsp.class);
	}

}
