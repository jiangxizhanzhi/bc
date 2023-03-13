package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.req.SituationComponentsAnimationAddReq;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationReq;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationUpdateReq;
import com.cyberpeace.situation.controller.res.SituationComponentsAnimationRes;
import com.cyberpeace.situation.dao.entity.SituationComponentsAnimation;

import java.util.List;

public interface SituationComponentAnimationService {


    String addComponentAnimation(SituationComponentsAnimationAddReq situationComponentsAnimationAddReq);

    SituationComponentsAnimationRes updateComponentAnimation(SituationComponentsAnimationUpdateReq situationComponentsAnimationUpdateReq);

    SituationComponentsAnimation getComponentAnimation(String resourceId);

    Integer deleteComponentAnimation(String resourceId);

    List<SituationComponentsAnimationRes> getComponentAnimations(SituationComponentsAnimationReq req);
}
