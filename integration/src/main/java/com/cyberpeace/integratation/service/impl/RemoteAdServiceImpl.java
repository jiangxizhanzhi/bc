package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;
import com.cyberpeace.integratation.service.bo.CompetitionDeleteBo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class RemoteAdServiceImpl implements RemoteAdService {

	@Autowired
	private AdEnvService adEnvService;

	private String EXAM_USER_FINISH_COUNT_URL;

	private String COMPETITION_LIST_URL;

	private String PRACTICE_COUNT_URL;

	private String COMPETITION_PAGE_URL;

	private String COMPETITION_DELETE_URL;

	@PostConstruct
	public void init() {
		PRACTICE_COUNT_URL = adEnvService.getAdHost()
				+ ConfigUtil.getContextProperty("ad.practice.count");
		EXAM_USER_FINISH_COUNT_URL = adEnvService.getAdHost()
				+ ConfigUtil.getContextProperty("ad.theory_race.statistic");
		COMPETITION_LIST_URL = adEnvService.getAdHost()
				+ ConfigUtil.getContextProperty("ad.competition.list");

		COMPETITION_PAGE_URL = adEnvService.getAdHost()
				+ ConfigUtil.getContextProperty("ad.competition.all");

		COMPETITION_DELETE_URL = adEnvService.getAdHost()
				+ ConfigUtil.getContextProperty("ad.competition.delete");

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
		bo.setCreateTime(json.getDate(JsonKeyConstant.CREATE_TIME));
		bo.setDesc(json.getString(JsonKeyConstant.DESC));
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
		JSONObject body = new JSONObject();
		body.put("start_time",startTime);
		endTime = TimeUtil.format6(DateUtils.addDays(TimeUtil.toDate2(endTime), CommonConstant.ONE));
		body.put("end_time", endTime);
		if(minScore != null){
			body.put("minScore",minScore);
		}
		if(userIds != null && userIds.size() !=0){
			body.put("user_ids",userIds.toArray(new String[userIds.size()]));
		}
		JSONArray jsonResponse = HttpClientUtil.post(EXAM_USER_FINISH_COUNT_URL,body,JSONArray.class);
		List<JSONObject> res = new ArrayList<>();
		for (Object object : (List) jsonResponse) {
			JSONObject obj = (JSONObject) object;
			res.add(obj);
		}
		return res;
	}

	@Override
	public Integer getAdPracticeCnt() {
		// 无竞赛环境则不调用
		if (StringUtils.isBlank(adEnvService.getAdHost())) {
			return null;
		}

		// 接口请求
		JSONObject jsonObject = HttpClientUtil.rawGet(PRACTICE_COUNT_URL, JSONObject.class);
		return Integer.parseInt(jsonObject.get("ques_num").toString());

	}


	@Override
	public ListRsp<CalendarCompetitionBo> listCalendarCompetitionBo(Page page, String competitionName) {
		// 接口请求
		String name = Objects.nonNull(competitionName) ? competitionName : CommonConstant.EMPTY_STRING;
		String formatUrl = String.format(COMPETITION_PAGE_URL, page.getSize(), name);
		JSONObject jsonObject = HttpClientUtil.rawGetForCompetition(formatUrl, JSONObject.class);
		JSONArray competitionArray = JsonUtil.toJsonObject(jsonObject.get("list").toString(), JSONArray.class);
		List<CalendarCompetitionBo> competitionBoList = StreamApiUtils.map(competitionArray, obj -> jsonToBo((JSONObject) obj));
		return PageUtil.getListResult(competitionBoList, page, Long.parseLong(jsonObject.get("total").toString()) );
	}

	@Override
	public Boolean batchDelete(List<String> ids) {

		List<String> notBlankIds = StreamApiUtils.filter(ids,
				StringUtils::isNotBlank);
		if (CollectionUtils.isEmpty(notBlankIds)) {
			return Boolean.TRUE;
		}
		CompetitionDeleteBo competitionDeleteBo = new CompetitionDeleteBo();
		competitionDeleteBo.setResource_ids(notBlankIds);
		String formatUrl = String.format(COMPETITION_DELETE_URL, ids);
		HttpClientUtil.deleteForCompetition(formatUrl, competitionDeleteBo, JSONObject.class);
		return true;
	}
}