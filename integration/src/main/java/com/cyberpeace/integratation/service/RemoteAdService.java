package com.cyberpeace.integratation.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;
import com.cyberpeace.integratation.service.bo.OpsQuestionBo;

/**
 * 竞赛外部接口调用
 */
public interface RemoteAdService {

	/**
	 * 查询时间范围内可查看的赛事
	 * @param startTime
	 * @param endTime
	 * @param userId 无 则查询所有
	 * @return
	 */
	List<CalendarCompetitionBo> competitionList(Date startTime, Date endTime, String userId);


	/**
	 * 查询指定用户在时间范围内不少于指定分数的理论赛次数
	 *
	 * @param startTime 起始时间
	 * @param endTime   截至时间
	 * @param userIds   用户ids
	 * @param minScore  最低分数
	 * @return 用户及其参与次数的map
	 */
	List<JSONObject> competitionListWithMinScore(String startTime, String endTime, List<String> userIds, Integer minScore);

	/**
	 * 查询指定用户的 考试通过率
	 *
	 * @param userIds   用户ids
	 * @return 用户及其参与次数的map
	 */
	Map<String, JSONObject> examPassList(List<String> userIds);

	JSONArray queryHistoryExamList();

    List<OpsQuestionBo> queryQuestionList();

	void clearUserData(String userId);

	JSONArray queryHistoryOpsList();
}
