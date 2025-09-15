package com.cyberpeace.cr.teamtrain.service;

import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonScoreStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamTimesStatisticsBo;

import java.util.List;

/**
 * xd项目团队训练统计Service
 *
 * @author SN
 * @date 2025/09/14
 */
public interface XdTeamTrainStatisticsService {

    List<XdTeamTimesStatisticsBo> getXdTeamTimes();

    List<XdTeamPersonStatisticsBo> getXdTeamPersonNum();

    List<XdTeamPersonScoreStatisticsBo> getPersonScore();

    List<XdTeamPersonScoreStatisticsBo> getPersonScoreSy();

    List<XdTeamPersonScoreStatisticsBo> getPersonScoreKm();

    List<XdTeamPersonScoreStatisticsBo> getPersonScoreZhyx();
}
