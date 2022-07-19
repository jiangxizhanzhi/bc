package com.cyberpeace.situation.service;


import com.cyberpeace.situation.controller.req.AddDynamicSchemeReq;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.controller.req.DynamicSchemeReq;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeRes;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.model.ws.WsAttackSchedule;

import java.io.IOException;
import java.util.List;

public interface DynamicSchemeService {

    String addDynamicScheme(AddDynamicSchemeReq addDynamicSchemeReq);

    DynamicSchemeRes getDynamicSchemeById(String resourceId);

    DynamicSchemeReq updateDynamicScheme(DynamicSchemeReq dynamicSchemeReq);

    void deleteDynamicScheme(String resourceId);

    List<DynamicSchemeNameRes> getAllDynamicName(String sceneId);

    void getLocalOption(OptionsRes optionsRes);

    void getRemoteOption(OptionsRes optionsRes, String sceneId);

    void pushMediaToRoom(AttackMessageReq attackMessageBo, List<String> nodeIds);

    void pushAttackToRoom(AttackMessageReq attackMessageBo);

    void pushDynamicSchemeToRoom(DynamicSchemeReq dynamicSchemeReq);

    void validDynamicSchemeName(AddDynamicSchemeReq addDynamicSchemeReq);

    List<WsAttackSchedule> getSchedule(String sceneId);

    void clearSceneData(String sceneId);

    void clearMedia(String sceneId);

    void validStage(DynamicSchemeReq dynamicSchemeReq);
}
