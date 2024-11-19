package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.rtks.service.bo.situation.LevelStatisticBo;
import com.cyberpeace.rtks.service.bo.situation.NewsBo;
import com.cyberpeace.rtks.service.bo.situation.RtksSituationRankings;
import com.cyberpeace.rtks.service.bo.situation.SituationTrendBo;

/**
 * RTKS态势Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface RtksSituationService {

	/**
	 * 读取动态
	 *
	 * @return
	 * @throws
	 * @see List<NewsBo>
	 */
	List<NewsBo> getNews();

	/**
	 * 获取态势排名
	 *
	 * @return
	 * @throws
	 * @see RtksSituationRankings
	 */
	RtksSituationRankings getRankings();

	/**
	 * 网安人才岗位态势：按一级level分类岗位及合格人数
	 *
	 * @return
	 * @throws
	 * @see List<LevelStatisticBo>
	 */
	List<LevelStatisticBo> getSituation();

	/**
	 * 记录-更新匹配度数据
	 *
	 * @param userId
	 * @param roleId
	 * @param match  火柴
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 */
	void recordMatch(String userId, String roleId, Integer match);

	/**
	 * 记录rtks态势信息，
	 *
	 * @param userId
	 * @param userName
	 * @param roleId
	 * @param roleName
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 * @see Integer
	 */
	Integer recordSituation(String userId, String userName, String roleId, String roleName);

	/**
	 * 网安人才发展趋势
	 *
	 * @return
	 * @throws
	 * @see SituationTrendBo
	 */
	SituationTrendBo getTrends();

}
