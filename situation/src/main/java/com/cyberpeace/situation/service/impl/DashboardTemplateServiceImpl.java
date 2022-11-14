package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.situation.controller.req.DashBoardTemplateReq;
import com.cyberpeace.situation.controller.req.DashboardComponentsReq;
import com.cyberpeace.situation.controller.req.DashboardTemplatePageReq;
import com.cyberpeace.situation.controller.res.DashboardTemplatePageRes;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.dao.entity.DashboardComponents;
import com.cyberpeace.situation.dao.entity.DashboardTemplate;
import com.cyberpeace.situation.dao.mapper.DashboardComponentsMapper;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateMapper;
import com.cyberpeace.situation.service.DashboardTemplateService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午3:38
 */
@Service
public class DashboardTemplateServiceImpl extends ServiceImpl<DashboardTemplateMapper, DashboardTemplate> implements DashboardTemplateService {

    @Autowired
    private DashboardTemplateMapper dashboardTemplateMapper;

    @Autowired
    private DashboardComponentsMapper dashboardComponentsMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public String createDashboardTemplate(DashBoardTemplateReq dashBoardTemplateReq) {
        DashboardTemplate dashboardTemplate = changeToDashboardTemplate(dashBoardTemplateReq);
        dashboardTemplate.setResourceId(IDGeneratorUtil.getID());
        String componentsSetting = dashboardTemplate.getComponentsSetting();
        JSONArray dashboardComponentsIdList = new JSONArray();
        List<DashboardComponents> dashboardComponentsList =
                JsonUtil.toJsonObjectArray(componentsSetting, DashboardComponents.class);
        for (DashboardComponents dashboardComponents : dashboardComponentsList) {
            String resourceId = IDGeneratorUtil.getID();
            dashboardComponents.setResourceId(resourceId);
            dashboardComponentsIdList.add(resourceId);
        }
        dashboardTemplate.setComponentsSetting(dashboardComponentsIdList.toString());
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                dashboardTemplateMapper.insert(dashboardTemplate);
                if (CollectionUtils.isNotEmpty(dashboardComponentsIdList)) {
                    dashboardComponentsMapper.batchInsert(dashboardComponentsList);
                }
            }
        });
        return dashboardTemplate.getResourceId();
    }

    @Override
    public String updateDashboardTemplate(DashBoardTemplateReq dashBoardTemplateReq) {
        String resourceId = dashBoardTemplateReq.getResourceId();
        DashboardTemplate dashboardTemplate = dashboardTemplateMapper.selectById(resourceId);
        if (dashboardTemplate == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        JSONArray newComponentsList = new JSONArray();
        List<String> oldComponentsList = JsonUtil.toJsonObjectArray(dashboardTemplate.getComponentsSetting(), String.class);
        List<DashboardComponentsReq> componentsSetting = dashBoardTemplateReq.getComponentsSetting();
        if (CollectionUtils.isNotEmpty(componentsSetting)) {
            for (DashboardComponentsReq dashboardComponentsReq : componentsSetting) {
                String newResourceId = IDGeneratorUtil.getID();
                newComponentsList.add(newResourceId);
                dashboardComponentsReq.setResourceId(newResourceId);
            }
        }
        DashboardTemplate toDashboardTemplate = changeToDashboardTemplate(dashBoardTemplateReq);
        toDashboardTemplate.setComponentsSetting(newComponentsList.toString());
        List<DashboardComponents> dashboardComponentsList = changeToDashboardComponents(componentsSetting);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                dashboardTemplateMapper.updateById(toDashboardTemplate);
                if (CollectionUtils.isNotEmpty(componentsSetting)) {
                    dashboardComponentsMapper.deleteBatchIds(oldComponentsList);
                    dashboardComponentsMapper.batchInsert(dashboardComponentsList);
                }
            }
        });

        return resourceId;
    }

    @Override
    public DashboardTemplateRes getDashboardTemplateInfo(String resourceId) {
        DashboardTemplate dashboardTemplate = dashboardTemplateMapper.selectById(resourceId);
        if (dashboardTemplate == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        String componentsSetting = dashboardTemplate.getComponentsSetting();
        List<String> list = JsonUtil.toJsonObjectArray(componentsSetting, String.class);
        DashboardTemplateRes dashboardTemplateRes = BeanUtils.copy(dashboardTemplate, DashboardTemplateRes.class);
        if (CollectionUtils.isNotEmpty(list)) {
            List<DashboardComponents> dashboardComponents = dashboardComponentsMapper.selectBatchIds(list);
            dashboardTemplateRes.setComponentsSetting(dashboardComponents);
        }
        return dashboardTemplateRes;
    }

    @Override
    public Integer deleteDashboardTemplate(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            List<DashboardTemplate> dashboardTemplates = dashboardTemplateMapper.selectBatchIds(idList);
            List<String> dashboardComponentsIdList = new ArrayList<>();
            dashboardTemplates.forEach(dashboardTemplate -> dashboardComponentsIdList.addAll(
                    JsonUtil.toJsonObjectArray(dashboardTemplate.getComponentsSetting(), String.class)));
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    dashboardTemplateMapper.deleteBatchIds(idList);
                    if (CollectionUtils.isNotEmpty(dashboardComponentsIdList)) {
                        dashboardComponentsMapper.deleteBatchIds(dashboardComponentsIdList);
                    }
                }
            });
        }
        return idList.size();
    }

    @Override
    public ListRsp<DashboardTemplatePageRes> getDashboardTemplatePage(DashboardTemplatePageReq dashboardTemplatePageReq) {
        Page page = dashboardTemplatePageReq.toPageParam();
        return PageUtil.getListResult(this.getBaseMapper().selectPage(page, dashboardTemplatePageReq));
    }


    private DashboardTemplate changeToDashboardTemplate(DashBoardTemplateReq dashBoardTemplateReq) {
        DashboardTemplate dashboardTemplate = BeanUtils.copy(dashBoardTemplateReq, DashboardTemplate.class);
        JSONArray jsonArray = new JSONArray();
        List<DashboardComponentsReq> componentsSetting = dashBoardTemplateReq.getComponentsSetting();
        if (CollectionUtils.isNotEmpty(componentsSetting)) {
            jsonArray.addAll(componentsSetting);
        }
        dashboardTemplate.setComponentsSetting(jsonArray.toString());
        return dashboardTemplate;
    }

    private List<DashboardComponents> changeToDashboardComponents(List<DashboardComponentsReq> dashboardComponentsReqList) {
        List<DashboardComponents> dashboardComponentsList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dashboardComponentsReqList)) {
            for (DashboardComponentsReq dashboardComponentsReq : dashboardComponentsReqList) {
                DashboardComponents dashboardComponents = BeanUtils.deepCopy(dashboardComponentsReq, DashboardComponents.class);
                dashboardComponentsList.add(dashboardComponents);
            }
        }

        return dashboardComponentsList;
    }
}
