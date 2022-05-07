package com.cyberpeace.integratation.service.impl;

import java.util.Date;
import java.util.List;

import com.cyberpeace.common.util.StreamApiUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.UrlBase64;
import org.bouncycastle.util.encoders.UrlBase64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;
import com.google.common.collect.Lists;

@Service
public class RemoteAdServiceImpl implements RemoteAdService {

	@Autowired
	private AdEnvService adEnvService;

	@Override
	public List<CalendarCompetitionBo> competitionList(Date startTime, Date endTime,
			String userId) {
		String competitionListUrl = adEnvService.getAdHost()
				+ ConfigUtil.getContextProperty("ad.competition.list");

		// 没配返回空
		if (StringUtils.isBlank(competitionListUrl)) {
			return Lists.newArrayList();
		}

		// 接口请求
		String formatUrl = String.format(competitionListUrl, TimeUtil.format1(startTime),
				TimeUtil.format1(endTime),
				StringUtils.isNotBlank(userId) ? userId : CommonConstant.EMPTY_STRING);

		JSONArray competitionArray = HttpClientUtil.rawGet(formatUrl, JSONArray.class);
		return StreamApiUtils.map(competitionArray, obj -> jsonToBo((JSONObject) obj));
	}

	/** 数据转换使用*/
	private CalendarCompetitionBo jsonToBo(JSONObject json) {
		CalendarCompetitionBo bo = new CalendarCompetitionBo();
		bo.setResourceId(json.getString(JsonKeyConstant.RESOURCE_ID));
		bo.setName(json.getString(JsonKeyConstant.NAME));
		bo.setCreateUserId(json.getString(JsonKeyConstant.CREATE_USER_ID));
		bo.setStartTime(json.getDate(JsonKeyConstant.START_TIME));
		bo.setEndTime(json.getDate(JsonKeyConstant.END_TIME));
		bo.setClassify(json.getInteger(JsonKeyConstant.CLASSIFY));
		bo.setStatus(json.getInteger(JsonKeyConstant.STATUS));
		bo.setType(json.getInteger(JsonKeyConstant.TYPE));
		return bo;
	}

}
