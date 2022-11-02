package com.cyberpeace.bs.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.TargetBo;
import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.mysql.cj.xdevapi.JsonArray;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LcServiceImpl implements LcService {

	/**
	 * 龙测请求对接地址
	 */
	private String LC_HOST_PORT;

	private String BEHAVIOR_LIST_URL;

	private String TARGET_LIST_URL;

	@PostConstruct
	public void init() {
		LC_HOST_PORT = SystemPropUtil.getProp("LC_HOST_PORT");
		BEHAVIOR_LIST_URL = LC_HOST_PORT + ConfigUtil.getContextProperty("lc.behavior.list");
		TARGET_LIST_URL = LC_HOST_PORT + ConfigUtil.getContextProperty("lc.target.list");
	}

	@Override
	public List<Behavior> getLcBehaviorList() {
		String targetUrl = BEHAVIOR_LIST_URL;
		JsonArray res = HttpClientUtil.rawGet(targetUrl, JsonArray.class);
		List<Behavior> ret = Lists.newArrayList();
		res.forEach(obj -> ret.add(BeanUtils.deepCopy(obj, Behavior.class)));
		return ret;
	}

	@Override
	public void startTraffic(String ip, String behaviorId) {
		// todo
	}

	@Override
	public void stopTraffic(String targetIp, String behaviorId) {
		// todo
	}

	@Override
	public List<TargetBo> getTargetList(String search) {
		String targetUrl = TARGET_LIST_URL + "/?search=" + search;
		JsonArray res = HttpClientUtil.rawGet(targetUrl, JsonArray.class);
		List<TargetBo> ret = Lists.newArrayList();
		// todo
		return ret;
	}
}
