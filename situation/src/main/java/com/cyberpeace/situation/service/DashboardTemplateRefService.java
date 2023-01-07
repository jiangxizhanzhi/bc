package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.req.DashBoardTemplateReq;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.controller.res.SituationPublishInstanceReviewRes;
import com.cyberpeace.situation.dao.entity.DashboardTemplateRef;
import com.cyberpeace.situation.service.context.DashboardTemplateContext;

import java.util.List;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午3:37
 */
public interface DashboardTemplateRefService {
    /**
     * 新增态势模板备份数据
     *
     * @param resourceId
     * @return
     */
    DashboardTemplateRef createDashboardTemplate(String resourceId);


    /**
     * 更新态势模板备份数据
     *
     * @param dashBoardTemplateReq
     * @return
     */
    String updateDashboardTemplate(DashBoardTemplateReq dashBoardTemplateReq);

    /**
     * 获取态势模板备份数据详情
     *
     * @param resourceId
     * @return
     */
    DashboardTemplateRes getDashboardTemplateInfo(String resourceId, String type);

    /**
     * 批量删除态势模板数据
     *
     * @param idList
     * @return
     */
    Integer deleteDashboardTemplate(List<String> idList);

    /**
     * 获取实时数据
     * @param situationPublishInstanceReviewRes
     */
    void exchangeOptionDataSet(SituationPublishInstanceReviewRes situationPublishInstanceReviewRes);

    DashboardTemplateContext getDashboardTemplateContext(String templateId);

}
