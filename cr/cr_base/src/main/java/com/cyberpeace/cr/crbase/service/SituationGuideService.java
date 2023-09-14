package com.cyberpeace.cr.crbase.service;


import com.cyberpeace.common.model.ListRsp;

import com.cyberpeace.cr.crbase.controller.req.guide.AddGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.GuideImplementReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ListGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.UpdateGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.rsp.AttackTopFiveRsp;
import com.cyberpeace.cr.crbase.controller.rsp.SituationGuideRes;

import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideScheme;

import java.util.List;

public interface SituationGuideService {

    ListRsp<SituationGuideScheme> listBySceneId(ListGuideSchemeReq req);

    String addGuideScheme(AddGuideSchemeReq addGuideSchemeReq);

    SituationGuideRes getGuideSchemeById(String resourceId);

    UpdateGuideSchemeReq updateGuideScheme(UpdateGuideSchemeReq updateGuideSchemeReq);

    void deleteGuideScheme(List<String> ids);

    void validGuideSchemeName(String name, String sceneId, String resourceId);

    void guideImplement(GuideImplementReq req);

    void pushDataToRoomByGuide(Boolean isInstance, SituationGuideHistory his);

    AttackTopFiveRsp attackTop5(String sceneId);

}
