package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.constant.WjErrorCode;
import com.cyberpeace.integratation.model.WjRecordRsp;
import com.cyberpeace.integratation.service.RemoteWjService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemoteWjServiceImpl implements RemoteWjService {

	private String WJ_IP;

	private String WJ_API_PORT;

	private String RECORD_LIST_URL;

	@PostConstruct
	public void init() {

		// 尝试获取挖掘平台的IP
		WJ_IP = SystemPropUtil.getProp("WJ_IP");
		if (StringUtils.isBlank(WJ_IP)) {
			log.error("WJ_IP is null");
			WJ_IP = "127.0.0.1";
		}

		WJ_API_PORT = ConfigUtil.getContextProperty("vuln.platform.api.port");

		RECORD_LIST_URL = "http://" + WJ_IP + ":" + WJ_API_PORT
				+ ConfigUtil.getContextProperty("vuln.platform.api.url.record.list");

	}

	@Override
	public ListRsp<WjRecordRsp> listWjRecord(PageReqBase req) {

		ListRsp<WjRecordRsp> listRsp = new ListRsp<>();

		try {
			JSONObject rep = HttpClientUtil.rawGetByParam(req, RECORD_LIST_URL, JSONObject.class);
			listRsp.setTotal(rep.getInteger("total"));
			listRsp.setLastPage(rep.getInteger("last_page"));
			listRsp.setCurrentPage(rep.getInteger("current_page"));
			listRsp.setList(JsonUtil.toJsonObjectArray(
					JsonUtil.toJsonString(rep.getJSONArray("list")), WjRecordRsp.class));

		} catch (Exception e) {
			log.error("Get wj record list error", e);
			throw new BusinessException(WjErrorCode.GET_WJ_RECORD_FAIL);
		}

		return listRsp;
	}
	
}
