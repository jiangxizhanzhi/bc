package com.cyberpeace.situation.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceAddReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceUpdateReq;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;

public interface SituationPublishInstanceService extends IService<SituationPublishInstance> {

    SituationPublishInstance insertOne(SituationPublishInstanceAddReq situationPublishInstanceAddReq);

    SituationPublishInstance updateOne(SituationPublishInstanceUpdateReq situationPublishInstanceUpdateReq);

    void checkPlan(SituationPublishInstanceAddReq situationPublishInstanceAddReq);
}
