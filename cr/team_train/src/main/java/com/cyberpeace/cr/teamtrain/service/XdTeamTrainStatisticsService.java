package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.TrainStatisticsDetailReq;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonScoreStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonalStatisticsBo;
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

    ListRsp<XdTeamPersonalStatisticsBo> getAllStatisticsDetail(TrainStatisticsDetailReq detailReq, Page page, Integer code);
}
