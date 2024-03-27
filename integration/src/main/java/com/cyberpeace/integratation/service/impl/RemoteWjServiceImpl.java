package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.*;
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
			Integer count = rep.getInteger("count");

			// 通过数据总数，计算总页数，如果数据总数为0，则总页数为1
			Integer lastPage;
			if (count == null || count == 0) {
				count = 0;
				lastPage = 1;
			} else {
				lastPage = MathUtils.divide(count ,req.getPerPage());
			}

			listRsp.setTotal(count);
			listRsp.setLastPage(lastPage);
			listRsp.setCurrentPage(req.getPage());
			listRsp.setList(JsonUtil.toJsonObjectArray(
					JsonUtil.toJsonString(rep.getJSONArray("data")), WjRecordRsp.class));

		} catch (Exception e) {
			log.error("Get wj record list error", e);
			throw new BusinessException(WjErrorCode.GET_WJ_RECORD_FAIL);
		}

		return listRsp;
	}
	
}
