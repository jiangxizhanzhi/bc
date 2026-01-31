package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.TrainStatisticsDetailReq;
import com.cyberpeace.cr.teamtrain.controller.req.XdStatisticsReq;
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

    List<XdTeamTimesStatisticsBo> getXdTeamTimes(XdStatisticsReq request);

    List<XdTeamPersonStatisticsBo> getXdTeamPersonNum(XdStatisticsReq request);

    List<XdTeamPersonScoreStatisticsBo> getPersonScore(XdStatisticsReq request);

    List<XdTeamPersonScoreStatisticsBo> getPersonScoreSy(XdStatisticsReq request);

    List<XdTeamPersonScoreStatisticsBo> getPersonScoreKm(XdStatisticsReq request);

    List<XdTeamPersonScoreStatisticsBo> getPersonScoreZhyx(XdStatisticsReq request);

    ListRsp<XdTeamPersonalStatisticsBo> getAllStatisticsDetail(TrainStatisticsDetailReq detailReq, Page page, Integer code);
}
