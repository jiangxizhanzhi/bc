package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.req.DashBoardTemplateReq;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;

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
    String createDashboardTemplate(String resourceId);


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
    DashboardTemplateRes getDashboardTemplateInfo(String resourceId);

    /**
     * 批量删除态势模板数据
     *
     * @param idList
     * @return
     */
    Integer deleteDashboardTemplate(List<String> idList);

}
