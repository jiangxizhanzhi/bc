package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;

import java.util.Date;
import java.util.List;

/**
 * 竞赛外部接口调用
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `RemoteAdService` 的接口，该接口包含两个方法，用于处理与竞赛相关的外部服务调用。以下是详细的分析： 
 * ### 接口名称 
 * - `RemoteAdService` 
 * ### 包名 
 * - `com.cyberpeace.integratation.service` 
 * ### 方法一：`competitionList` 
 * - **描述**：查询在指定时间范围内的可查看的赛事。 
 * - **参数** 
 * - `Date startTime`: 赛事开始时间。 
 * - `Date endTime`: 赛事结束时间。 
 * - `String userId`: 用户ID（可选，如果为 `null` 或未提供，则查询所有用户的赛事）。 
 * - **返回值** 
 * - `List<CalendarCompetitionBo>`: 返回符合条件的赛事列表。 
 * ### 方法二：`competitionListWithMinScore` 
 * - **描述**：查询指定用户在指定时间范围内达到或超过最低分数的比赛次数。 
 * - **参数** 
 * - `String startTime`: 起始时间。 
 * - `String endTime`: 结束时间。 
 * - `List<String> userIds`: 用户ID列表。 
 * - `Integer minScore`: 最低分数。 
 * - **返回值** 
 * - `List<JSONObject>`: 返回每个用户及其参与次数的映射信息。 
 * ### 类型别名 
 * - `CalendarCompetitionBo`: 一个业务对象类，用于封装竞赛相关信息。 
 * - `JSONObject`: 阿里巴巴的 FastJSON 库中的 JSON 对象，用于表示复杂的数据结构。 
 * ### 总结 
 * 这个接口主要用于查询竞赛数据，包括按时间段查询所有赛事以及按用户和最低分数查询符合条件的比赛次数。这些方法能够帮助前端或其他服务获取所需的数据，以便进行展示或进一步处理。 
**/
public interface RemoteAdService {

	/**
	 * 查询时间范围内可查看的赛事
	 *
	 * @param startTime
	 * @param endTime
	 * @param userId    无 则查询所有
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

}

