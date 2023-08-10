package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;

import java.util.Date;
import java.util.List;

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
	 * @return 竞赛习题数和
	 */
	Integer getAdPracticeCnt();

	/**
	 * @return 竞赛习题数和
	 */
	ListRsp<CalendarCompetitionBo> listCalendarCompetitionBo(Page page, String name);

}
