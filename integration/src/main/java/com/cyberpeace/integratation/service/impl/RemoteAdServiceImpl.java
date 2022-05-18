package com.cyberpeace.integratation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

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

	private String EXAM_USER_FINISH_COUNT_URL;

	private String COMPETITION_LIST_URL;

	@PostConstruct
	public void init() {
		EXAM_USER_FINISH_COUNT_URL = adEnvService.getAdHost()
				+ ConfigUtil.getContextProperty("ad.theory_race.statistic") + "?";
		COMPETITION_LIST_URL = adEnvService.getAdHost()
				+ ConfigUtil.getContextProperty("ad.competition.list");
	}

	@Override
	public List<CalendarCompetitionBo> competitionList(Date startTime, Date endTime,
			String userId) {

		// 无竞赛环境则不调用
		if (StringUtils.isBlank(adEnvService.getAdHost())) {
			return Lists.newArrayList();
		}

		// 接口请求
		String formatUrl = String.format(COMPETITION_LIST_URL, TimeUtil.format1(startTime),
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
	public List<JSONObject> competitionListWithMinScore(String startTime, String endTime,
			List<String> userIds, Integer minScore) {

		// 未配置则返回空列表
		if (StringUtils.isBlank(adEnvService.getAdHost())) {
			return Lists.newArrayList();
		}

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
		List<JSONObject> res = new ArrayList<>();
		for (Object object : (List) jsonResponse) {
			JSONObject obj = (JSONObject) object;
			res.add(obj);
		}
		return res;
	}

}
