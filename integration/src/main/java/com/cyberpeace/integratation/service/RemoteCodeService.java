package com.cyberpeace.integratation.service;

import java.util.List;

import com.cyberpeace.integratation.model.situation.res.EventListRsp;

public interface RemoteCodeService {

	/**
	 * 获取特定事件编码列表
	 * @return List<EventListRsp>
	 */
	List<EventListRsp> getEvents();

}
