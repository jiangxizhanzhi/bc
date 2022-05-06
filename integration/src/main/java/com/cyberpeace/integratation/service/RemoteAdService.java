package com.cyberpeace.integratation.service;

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

}
