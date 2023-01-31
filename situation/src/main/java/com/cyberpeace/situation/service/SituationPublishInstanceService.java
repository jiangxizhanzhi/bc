package com.cyberpeace.situation.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceAddReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceFilterReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceUpdateReq;
import com.cyberpeace.situation.controller.res.SituationPublishInstancePageRes;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;

public interface SituationPublishInstanceService extends IService<SituationPublishInstance> {

    SituationPublishInstance selectReleasedBySubjectId(String taskId, String subjectId);

    SituationPublishInstance insertOne(SituationPublishInstanceAddReq situationPublishInstanceAddReq);

    SituationPublishInstance updateOne(SituationPublishInstanceUpdateReq situationPublishInstanceUpdateReq);

    /**
     * 清除未使用的态势
     */
    void deleteUnusedSituation();

    /**
     * 分页查询
     *
     * @return
     */
    ListRsp<SituationPublishInstancePageRes> getSituationPublishInstancePage(SituationPublishInstanceFilterReq situationPublishInstanceFilterReq);

    String getEnvIdByDashboardId(String dashboardId);
}
