package com.cyberpeace.situation.service;


import com.cyberpeace.situation.controller.req.DynamicSchemeReq;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.controller.req.UpdateDynamicSchemeReq;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeRes;
import com.cyberpeace.situation.controller.res.OptionsRes;

import java.util.List;

public interface DynamicSchemeService {

    void addDynamicScheme(DynamicSchemeReq dynamicSchemeReq);

    DynamicSchemeRes getDynamicSchemeById(String resourceId);

    void updateDynamicScheme(UpdateDynamicSchemeReq updateDynamicSchemeReq);

    void deleteDynamicScheme(String resourceId);

    List<DynamicSchemeNameRes> getAllDynamicName();
}
