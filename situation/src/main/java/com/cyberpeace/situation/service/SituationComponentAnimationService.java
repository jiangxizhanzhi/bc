package com.cyberpeace.situation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationAddReq;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationReq;
import com.cyberpeace.situation.controller.req.SituationComponentsAnimationUpdateReq;
import com.cyberpeace.situation.controller.res.SituationComponentsAnimationRes;
import com.cyberpeace.situation.controller.res.SituationComponentsChartDetailsRes;
import com.cyberpeace.situation.dao.entity.SituationComponentsAnimation;
import com.cyberpeace.situation.dao.entity.TwoDimensionalComponents;

import java.util.List;

public interface SituationComponentAnimationService extends IService<SituationComponentsAnimation> {


    String addComponentAnimation(SituationComponentsAnimationAddReq situationComponentsAnimationAddReq);

    SituationComponentsAnimationRes updateComponentAnimation(SituationComponentsAnimationUpdateReq situationComponentsAnimationUpdateReq);

    SituationComponentsAnimation getComponentAnimation(String resourceId);

    Integer deleteComponentAnimation(List<String> resourceIds);

    List<SituationComponentsAnimationRes> getComponentAnimations(SituationComponentsAnimationReq req);

    List<SituationComponentsChartDetailsRes> getChartDetails();
}
