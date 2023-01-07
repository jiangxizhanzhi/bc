package com.cyberpeace.situation.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.controller.req.DashBoardTemplateReq;
import com.cyberpeace.situation.controller.req.DashboardComponentsReq;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.controller.res.SituationPublishInstanceReviewRes;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;
import com.cyberpeace.situation.dao.entity.DashboardComponents;
import com.cyberpeace.situation.dao.entity.DashboardTemplateRef;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.dao.mapper.DashboardAttributeMapper;
import com.cyberpeace.situation.dao.mapper.DashboardComponentsMapper;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateRefMapper;
import com.cyberpeace.situation.dao.mapper.SituationPublishInstanceMapper;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.DashboardTemplateService;
import com.cyberpeace.situation.service.context.DashboardTemplateContext;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午3:38
 */
@Service
public class DashboardTemplateServiceRefImpl extends ServiceImpl<DashboardTemplateRefMapper, DashboardTemplateRef> implements DashboardTemplateRefService {

    @Autowired
    private DashboardTemplateRefMapper dashboardTemplateRefMapper;

    @Autowired
    private DashboardComponentsMapper dashboardComponentsMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private DashboardTemplateService dashboardTemplateService;

    @Autowired
    private SituationPublishInstanceMapper situationPublishInstanceMapper;

    @Autowired
    private DashboardAttributeMapper dashboardAttributeMapper;

    @Override
    public DashboardTemplateRef createDashboardTemplate(String resourceId) {

        //获取模板数据
        DashboardTemplateRes dashboardTemplateInfo = dashboardTemplateService.getDashboardTemplateInfo(resourceId, null);
        DashBoardTemplateReq dashBoardTemplateReq = BeanUtils.copy(dashboardTemplateInfo, DashBoardTemplateReq.class);
        DashboardTemplateRef dashboardTemplateRef = changeToDashboardTemplateRef(dashBoardTemplateReq);

        //设置备份数据
        dashboardTemplateRef.setResourceId(IDGeneratorUtil.getID());
        JSONArray dashboardComponentsIdList = new JSONArray();
        List<DashboardComponents> dashboardComponentsList = dashboardTemplateInfo.getComponentsSetting();

        if (CollectionUtils.isNotEmpty(dashboardComponentsList)) {
            for (DashboardComponents dashboardComponents : dashboardComponentsList) {
                String componentsResourceId = IDGeneratorUtil.getID();
                dashboardComponents.setResourceId(componentsResourceId);
                dashboardComponentsIdList.add(componentsResourceId);
            }
        }

        dashboardTemplateRef.setEditStatus(dashboardTemplateInfo.getEditStatus());
        dashboardTemplateRef.setComponentsSetting(dashboardComponentsIdList.toString());
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                dashboardTemplateRefMapper.insert(dashboardTemplateRef);
                if (CollectionUtils.isNotEmpty(dashboardComponentsIdList)) {
                    dashboardComponentsMapper.batchInsert(dashboardComponentsList);
                }
            }
        });
        return dashboardTemplateRef;
    }

    @Override
    public String updateDashboardTemplate(DashBoardTemplateReq dashBoardTemplateReq) {
        String resourceId = dashBoardTemplateReq.getResourceId();
        DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefMapper.selectById(resourceId);
        if (dashboardTemplateRef == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        JSONArray newComponentsList = new JSONArray();
        List<String> oldComponentsList = JsonUtil.toJsonObjectArray(dashboardTemplateRef.getComponentsSetting(), String.class);
        List<DashboardComponentsReq> componentsSetting = dashBoardTemplateReq.getComponentsSetting();
        if (CollectionUtils.isNotEmpty(componentsSetting)) {
            for (DashboardComponentsReq dashboardComponentsReq : componentsSetting) {
                String newResourceId = IDGeneratorUtil.getID();
                newComponentsList.add(newResourceId);
                dashboardComponentsReq.setResourceId(newResourceId);
            }
        }
        DashboardTemplateRef toDashboardTemplateRef = changeToDashboardTemplateRef(dashBoardTemplateReq);
        toDashboardTemplateRef.setComponentsSetting(newComponentsList.toString());
        List<DashboardComponents> dashboardComponentsList = changeToDashboardComponents(componentsSetting);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                dashboardTemplateRefMapper.updateById(toDashboardTemplateRef);
                if (CollectionUtils.isNotEmpty(oldComponentsList)) {
                    dashboardComponentsMapper.deleteBatchIds(oldComponentsList);
                }
                if (CollectionUtils.isNotEmpty(dashboardComponentsList)) {
                    dashboardComponentsMapper.batchInsert(dashboardComponentsList);
                }
            }
        });
        return resourceId;
    }

    @Override
    public DashboardTemplateRes getDashboardTemplateInfo(String resourceId, String type) {
        DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefMapper.selectById(resourceId);
        if (dashboardTemplateRef == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        String componentsSetting = dashboardTemplateRef.getComponentsSetting();
        List<String> list = JsonUtil.toJsonObjectArray(componentsSetting, String.class);
        DashboardTemplateRes dashboardTemplateRes = BeanUtils.copy(dashboardTemplateRef, DashboardTemplateRes.class);
        //图层排序，默认将GIS图层置为最底层
        if (CollectionUtils.isNotEmpty(list)) {
            List<DashboardComponents> dashboardComponents = dashboardComponentsMapper.selectBatchIds(list);
            List<DashboardComponents> sourceDashboardComponents = new ArrayList<>();
            List<DashboardComponents> sortDashboardComponents = new ArrayList<>();
            List<DashboardComponents> commonDashboardComponentList = new ArrayList<>();
            for (DashboardComponents dashboardComponent : dashboardComponents) {
                //视角筛选
                String sourceType = dashboardComponent.getType();
                if (type != null && !Objects.equals(sourceType, type)) {
                    continue;
                }
                sourceDashboardComponents.add(dashboardComponent);
                String componentSetting = dashboardComponent.getComponentSetting();
                JSONObject jsonObject = JSONObject.parseObject(componentSetting);
                String componentType = jsonObject.getString("component_type");
                if (componentType != null && (Objects.equals("0", componentType) || Objects.equals("1", componentType))) {
                    sortDashboardComponents.add(dashboardComponent);
                }
                if (StringUtils.isNotBlank(type) && Objects.equals(type, "all")){
                    commonDashboardComponentList.add(dashboardComponent);
                }
            }
            sourceDashboardComponents.removeAll(sortDashboardComponents);
            sourceDashboardComponents.removeAll(commonDashboardComponentList);
            //按图层进行排序
            List<DashboardComponents> dashboardComponentsList =
                    sourceDashboardComponents.stream().sorted(Comparator.comparing(DashboardComponents::getComponentIndex)).collect(Collectors.toList());

            //添加二位组件配置
            sortDashboardComponents.addAll(dashboardComponentsList);

            //添加全局控件和公共控件
            sortDashboardComponents.addAll(commonDashboardComponentList);

            dashboardTemplateRes.setComponentsSetting(sortDashboardComponents);


        }
        return dashboardTemplateRes;
    }

    @Override
    public Integer deleteDashboardTemplate(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            List<DashboardTemplateRef> dashboardTemplateRefs = dashboardTemplateRefMapper.selectBatchIds(idList);
            List<String> dashboardComponentsIdList = new ArrayList<>();
            dashboardTemplateRefs.forEach(dashboardTemplateRef -> dashboardComponentsIdList.addAll(
                    JsonUtil.toJsonObjectArray(dashboardTemplateRef.getComponentsSetting(), String.class)));
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    dashboardTemplateRefMapper.deleteBatchIds(idList);
                    if (CollectionUtils.isNotEmpty(dashboardComponentsIdList)) {
                        dashboardComponentsMapper.deleteBatchIds(dashboardComponentsIdList);
                    }
                }
            });
        }
        return idList.size();
    }

    private DashboardTemplateRef changeToDashboardTemplateRef(DashBoardTemplateReq dashBoardTemplateReq) {
        DashboardTemplateRef dashboardTemplateRef = BeanUtils.copy(dashBoardTemplateReq, DashboardTemplateRef.class);
        JSONArray jsonArray = new JSONArray();
        List<DashboardComponentsReq> componentsSetting = dashBoardTemplateReq.getComponentsSetting();
        if (CollectionUtils.isNotEmpty(componentsSetting)) {
            jsonArray.addAll(componentsSetting);
        }
        dashboardTemplateRef.setComponentsSetting(jsonArray.toString());
        return dashboardTemplateRef;
    }

    private List<DashboardComponents> changeToDashboardComponents(List<DashboardComponentsReq> dashboardComponentsReqList) {
        List<DashboardComponents> dashboardComponentsList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dashboardComponentsReqList)) {
            for (DashboardComponentsReq dashboardComponentsReq : dashboardComponentsReqList) {
                DashboardComponents dashboardComponents = BeanUtils.deepCopy(dashboardComponentsReq, DashboardComponents.class);
                Object componentSetting = dashboardComponentsReq.getComponentSetting();
                Object datasourceSetting = dashboardComponentsReq.getDatasourceSetting();
                if (componentSetting != null) {
                    dashboardComponents.setComponentSetting(JsonUtil.toJsonString(componentSetting));
                }
                if (datasourceSetting != null) {
                    dashboardComponents.setDatasourceSetting(JsonUtil.toJsonString(dashboardComponents));
                }
                dashboardComponentsList.add(dashboardComponents);
            }
        }
        return dashboardComponentsList;
    }

    public void exchangeOptionDataSet(SituationPublishInstanceReviewRes situationPublishInstanceReviewRes) {
        DashboardTemplateRes dashboardTemplateRes = situationPublishInstanceReviewRes.getTemplateInfo();
        String taskId = situationPublishInstanceReviewRes.getTaskId();
        String subjectId = situationPublishInstanceReviewRes.getSubjectId();
        JSONArray jsonArray = new JSONArray();
        JSONObject taskJson = new JSONObject();
        JSONObject subjectJson = new JSONObject();
        taskJson.put("name", "taskId");
        taskJson.put("value", taskId);
        subjectJson.put("name", "subjectId");
        subjectJson.put("value", subjectId);
        jsonArray.add(taskJson);
        jsonArray.add(subjectJson);
        List<DashboardComponents> componentsSettingList = dashboardTemplateRes.getComponentsSetting();
        if (CollectionUtils.isNotEmpty(componentsSettingList)) {
            for (DashboardComponents dashboardComponent : componentsSettingList) {
                String componentSetting = dashboardComponent.getComponentSetting();
                JSONObject jsonObject = JSONObject.parseObject(componentSetting);
                String componentType = jsonObject.getString("component_type");
                if (componentType== null) {
                    JSONArray echartsOption = dashboardTemplateService.getEchartsOption(componentSetting, jsonArray.toJSONString());
                    String option = jsonObject.getString("option");
                    JSONObject optionObject = JSONObject.parseObject(option);
                    JSONObject dataSetObject = new JSONObject();
                    dataSetObject.put("source", echartsOption);
                    optionObject.replace("dataset", dataSetObject);
                }
            }
        }
    }

    @Override
    public DashboardTemplateContext getDashboardTemplateContext(String templateId) {
        SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper
                .selectByTemplateId(templateId);
        if (situationPublishInstance == null) {
            return null;
        }
        DashboardAttribute dashboardAttribute = dashboardAttributeMapper
                .getByResourceId(situationPublishInstance.getAttributeId());
        return new DashboardTemplateContext(situationPublishInstance, dashboardAttribute);
    }
}
