package com.cyberpeace.situation.service;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.ComponentsDataReq;
import com.cyberpeace.situation.controller.req.DashBoardTemplateReq;
import com.cyberpeace.situation.controller.req.DashboardTemplatePageReq;
import com.cyberpeace.situation.controller.res.DashboardTemplatePageRes;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;

import java.util.List;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午3:37
 */
public interface DashboardTemplateService {
    /**
     * 新增态势模板
     *
     * @param dashBoardTemplateReq
     * @return
     */
    String createDashboardTemplate(DashBoardTemplateReq dashBoardTemplateReq);


    /**
     * 更新态势模板
     *
     * @param dashBoardTemplateReq
     * @return
     */
    String updateDashboardTemplate(DashBoardTemplateReq dashBoardTemplateReq);

    /**
     * 获取态势模板
     *
     * @param resourceId
     * @return
     */
    DashboardTemplateRes getDashboardTemplateInfo(String resourceId);

    /**
     * 批量态势模板
     *
     * @param idList
     * @return
     */
    Integer deleteDashboardTemplate(List<String> idList);

    /**
     * 分页查询态势模板
     *
     * @param dashboardTemplatePageReq
     * @return
     */
    ListRsp<DashboardTemplatePageRes> getDashboardTemplatePage(DashboardTemplatePageReq dashboardTemplatePageReq);

    /**
     * 获取所有组件配置id
     *
     * @param resourceId
     * @return
     */
    List<String> getComponentsList(String resourceId);

    /**
     * 获取echarts数据结构
     *
     * @param componentsDataReq
     * @return
     */
    JSONArray getComponentsEchartsData(ComponentsDataReq componentsDataReq);

}
