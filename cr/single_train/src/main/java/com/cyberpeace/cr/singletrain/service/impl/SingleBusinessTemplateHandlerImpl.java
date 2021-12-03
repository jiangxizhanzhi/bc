package com.cyberpeace.cr.singletrain.service.impl;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainTemplateReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.service.SingleTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleBusinessTemplateHandlerImpl extends BusinessTemplateHandler {
    @Autowired
    private SingleTemplateService singleTemplateService;

    public BusinessTemplate.BusinessType setBusinessType() {
        return BusinessTemplate.BusinessType.TRAINING_SINGLE;
    }

    @Override
    public String saveRealBusiness(BasicTemplate basicTemplate, String businessId) {
        SingleTemplate singleTemplate = new SingleTemplate();
        BaseInfo baseInfo = basicTemplate.getBaseInfo();

        singleTemplate.setBusinessTemplateId(businessId);
        singleTemplate.setPreSkills(JsonUtil.toJsonString(baseInfo.getPreSkill()));
        singleTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfo.getTrainSkill()));
        singleTemplate.setSubType(baseInfo.getCategory());
        singleTemplate.setDurationTime(baseInfo.getDurationTime());

        SingleTrainTemplateReq requestData = (SingleTrainTemplateReq) basicTemplate;
        singleTemplate.setMissionsScheme(JsonUtil.toJsonString(requestData.getMissionsScheme()));
        // todo save missions -> return mission_id ? -> singleTemplate.setMissionSchemeId(mission_id)

        singleTemplateService.save(singleTemplate);

        return singleTemplate.getBusinessTemplateId();
    }
}
