/**
 * 这段代码定义了一个名为 `RemoteAdServiceImpl` 的 Spring 服务类，实现了 `RemoteAdService` 接口。该类主要用于处理与远程竞赛相关的操作。以下是详细的分析： 
 * ### 类和成员变量 
 * - **类名**: `RemoteAdServiceImpl` 
 * - **注解**: 
 * - `@Slf4j`: 使用 Lombok 自动生成日志记录器。 
 * - `@Service`: 表明这是一个 Spring 服务类。 
 * - **成员变量**: 
 * - `adEnvService`: 自动注入的 `AdEnvService` 对象，用于获取广告环境的主机地址。 
 * - `EXAM_USER_FINISH_COUNT_URL`: 考试用户完成计数的 URL。 
 * - `COMPETITION_LIST_URL`: 比赛列表的 URL。 
 * ### 方法 
 * 1. **初始化方法 (`init`)**: 
 * - 在构造函数执行后调用，用于初始化 `EXAM_USER_FINISH_COUNT_URL` 和 `COMPETITION_LIST_URL`。 
 * 2. **比赛名单方法 (`competitionList`)**: 
 * - 输入参数: 开始时间、结束时间和用户ID。 
 * - 如果没有竞赛环境，则返回空列表。 
 * - 构造并发送 GET 请求到 `COMPETITION_LIST_URL`，并将响应转换为 `CalendarCompetitionBo` 对象列表。 
 * 3. **数据转换方法 (`jsonToBo`)**: 
 * - 将 JSON 对象转换为 `CalendarCompetitionBo` 对象，并设置各个属性值。 
 * 4. **最低得分比赛列表方法 (`competitionListWithMinScore`)**: 
 * - 输入参数: 开始时间、结束时间、用户ID列表和最低分数。 
 * - 如果没有竞赛环境，则返回空列表。 
 * - 构造并发送 POST 请求到 `EXAM_USER_FINISH_COUNT_URL`，并将响应转换为 `JSONObject` 列表。 
 * ### 总结 
 * - **功能**: 处理与远程竞赛相关的操作，包括获取比赛列表和最低得分比赛列表。 
 * - **依赖**: 使用了多个工具类（如 `ConfigUtil`, `HttpClientUtil`, `StreamApiUtils`, `TimeUtil`）来辅助完成任务。 
 * - **设计**: 使用了依赖注入和自动初始化机制，使得类的实例化和初始化过程清晰简洁。 
 * - **技术栈**: 使用了阿里巴巴的 FastJSON 进行 JSON 处理，以及 Apache Commons Lang3 进行日期和字符串操作。 
 * 总体来说，这个类的设计合理，功能明确，且使用了多种辅助工具简化了业务逻辑的实现。 
**/
package com.cyberpeace.integratation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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

/**
 * 远程竞赛Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Service
public class RemoteAdServiceImpl implements RemoteAdService {

	/**
	 * 竞赛环境Service
	 */
	@Autowired
	private AdEnvService adEnvService;

	/**
	 * 考试用户完成计数网址
	 */
	private String EXAM_USER_FINISH_COUNT_URL;

	/**
	 * 比赛列表 URL
	 */
	private String COMPETITION_LIST_URL;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		EXAM_USER_FINISH_COUNT_URL = adEnvService.getAdHost()
				+ ConfigUtil.getContextProperty("ad.theory_race.statistic");
		COMPETITION_LIST_URL = adEnvService.getAdHost()
				+ ConfigUtil.getContextProperty("ad.competition.list");
	}

	/**
	 * 比赛名单
	 *
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @param userId    用户id
	 * @return {@code List<CalendarCompetitionBo> }
	 */
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

	/**
	 * 数据转换使用
	 *
	 * @param json JSON 格式
	 * @return {@code CalendarCompetitionBo }
	 */
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

	/**
	 * 最低得分比赛列表
	 *
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @param userIds   用户id列表
	 * @param minScore  最低分数
	 * @return {@code List<JSONObject> }
	 */
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

}
