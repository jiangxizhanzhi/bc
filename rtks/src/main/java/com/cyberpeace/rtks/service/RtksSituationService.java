package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.rtks.service.bo.situation.LevelStatisticBo;
import com.cyberpeace.rtks.service.bo.situation.NewsBo;
import com.cyberpeace.rtks.service.bo.situation.RtksSituationRankings;

public interface RtksSituationService {

	/**
	 * 读取动态
	 * @return
	 */
	List<NewsBo> getNews();

	/**
	 * 获取态势排名
	 * @return
	 */
	RtksSituationRankings getRankings();

	/**
	 * 网安人才岗位态势：按一级level分类岗位及合格人数
	 * @return
	 */
	List<LevelStatisticBo> getSituation();

	/**
	 * 记录rtks态势信息，
	 * @param userId 
	 * @param userName
	 * @param roleId
	 * @param roleName
	 * @return
	 */
	Integer recordSituation(String userId, String userName, String roleId, String roleName);

}
