package com.cyberpeace.cr.crbase.service;


import com.cyberpeace.cr.crbase.controller.req.guide.AddGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.GuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.rsp.SituationGuideRes;

import java.util.List;

public interface GuideSchemeService {

    String addGuideScheme(AddGuideSchemeReq addGuideSchemeReq);

    SituationGuideRes getGuideSchemeById(String resourceId);

    GuideSchemeReq updateGuideScheme(GuideSchemeReq guideSchemeReq);

    void deleteGuideScheme(List<String> ids);

    void validGuideSchemeName(String name, String sceneId, String resourceId);
}
