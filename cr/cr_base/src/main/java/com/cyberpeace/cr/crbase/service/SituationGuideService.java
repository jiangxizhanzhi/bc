package com.cyberpeace.cr.crbase.service;


import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.guide.AddGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.GuideImplementReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ListGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.UpdateGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.rsp.guide.AttackTopFiveRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideRoleStageRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideStatisticsRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.SituationGuideRes;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideScheme;

import java.util.List;

public interface SituationGuideService {

    ListRsp<SituationGuideScheme> listByRoomId(ListGuideSchemeReq req);

    String addGuideScheme(AddGuideSchemeReq addGuideSchemeReq);

    SituationGuideRes getGuideSchemeById(String resourceId);

    UpdateGuideSchemeReq updateGuideScheme(UpdateGuideSchemeReq updateGuideSchemeReq);

    void deleteGuideScheme(List<String> ids);

    void validGuideSchemeName(String name, String roomId, String resourceId);

    void guideImplement(GuideImplementReq req);

    SituationGuideHistory getGuideHisByMissionId(String missionId,String taskId);

    void pushDataToRoomByGuide(Boolean isInstance, SituationGuideHistory his);

    List<AttackTopFiveRsp> attackTop5(String roomId);

    List<GuideRoleStageRsp>  missionGuide(String roomId, Integer businessType);

    List<GuideStatisticsRsp> guideStatistics(String roomId);

}
