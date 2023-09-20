package com.cyberpeace.cr.crbase.service;


import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.guide.*;
import com.cyberpeace.cr.crbase.controller.rsp.guide.*;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.service.context.guide.MpEvent;

import java.util.List;

public interface SituationGuideService {

    ListRsp<SituationGuideListRsp> listByRoomId(ListGuideSchemeReq req);

    String addGuideScheme(AddGuideSchemeReq addGuideSchemeReq);

    SituationGuideRsp getGuideSchemeDetail(String resourceId);

    UpdateGuideSchemeReq updateGuideScheme(UpdateGuideSchemeReq updateGuideSchemeReq);

    Boolean deleteGuideScheme(List<String> ids);

    Boolean guideImplement(GuideImplementReq req, boolean isTest);

    SituationGuideHistory getGuideHisByMissionId(String missionId, String taskId);

    Boolean pushDataToRoomByGuide(Boolean isInstance, SituationGuideHistory his);

    List<AttackTopFiveRsp> attackTop5(String roomId);

    List<GuideRoleStageRsp> missionGuide(String roomId, Integer businessType);

    GuideStatisticsRsp guideStatistics(String roomId);

    GuideTimeDistributionRsp guideTimeDistribution(String roomId);

    GuideParamTypeRsp getParamType(String roomId, Integer businessType);

    SituationModelRsp listModel();

    ListRsp<GuideHistoryRsp> guideRecord(GuideRecordReq pageReq);

    List<MpEvent> getMpEventList(ScreenGuideHistoryReq screenGuideHistoryReq);

    List<GuideHistoryRsp> mpEventToGuideHistoryRsp(List<MpEvent> mpEventList);

    List<SituationGuideHistory> guideHistoryRspToSituationGuideHistory(List<GuideHistoryRsp> guideHistoryRspList);
}
