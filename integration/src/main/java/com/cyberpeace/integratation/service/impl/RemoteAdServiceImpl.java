package com.cyberpeace.integratation.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemoteAdServiceImpl implements RemoteAdService {

	@Autowired
	private AdEnvService adEnvService;

	private String AD_HOST;

	private String AD_PORT;

	private String EXAM_USER_FINISH_COUNT_URL;

	@PostConstruct
	public void init() {
		AD_HOST = SystemPropUtil.getProp("AD_HOST");
		AD_PORT = SystemPropUtil.getProp("AD_PORT");
		EXAM_USER_FINISH_COUNT_URL = adEnvService.getAdHost() + ConfigUtil.getContextProperty("ad.theory_race.statistic") + "?";
	}

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

		JSONArray competitionArray = HttpClientUtil.rawSpecialGet(formatUrl, JSONArray.class);
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

	@Override
	public List<JSONObject> competitionListWithMinScore(String startTime, String endTime, List<String> userIds, Integer minScore) {

		// 获取竞赛理论赛所有百分制换算后>=80分的数据
		StringBuilder targetUrl = new StringBuilder(EXAM_USER_FINISH_COUNT_URL);
		List<String> queryParams = Lists.newArrayList();
		// 拼接请求参数
		if (minScore != null) {
			queryParams.add(String.format("minScore=%s", minScore));
		}
		queryParams.add(String.format("start_time=%s", startTime));
		queryParams.add(String.format("end_time=%s", endTime));
		if (null != userIds && userIds.size() != 0) {
			userIds.forEach(userId -> {
				queryParams.add(String.format("user_ids=%s", userId));
			});
		}
		targetUrl.append(String.join("&", queryParams));

		JSONArray jsonResponse = HttpClientUtil.rawGet(targetUrl.toString(), JSONArray.class);
		List jsonArray = (List) jsonResponse;
		List<JSONObject> res = new ArrayList<>();
		for (Object object : jsonArray) {
			JSONObject obj = (JSONObject) object;
			res.add(obj);
		}
		return res;
	}

}
