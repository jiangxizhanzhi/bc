package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.DashboardTemplateEchartsData;
import com.cyberpeace.situation.controller.res.DashboardTemplatePageRes;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.dao.entity.*;
import com.cyberpeace.situation.dao.mapper.ApiRequestMapper;
import com.cyberpeace.situation.dao.mapper.DashboardComponentsMapper;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateMapper;
import com.cyberpeace.situation.dao.mapper.TwoDimensionalComponentsMapper;
import com.cyberpeace.situation.service.ApiRequestService;
import com.cyberpeace.situation.service.DashboardTemplateService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private ApiRequestService apiRequestService;
    @Autowired
    private TwoDimensionalComponentsMapper twoDimensionalComponentsMapper;
    @Autowired
    private ApiRequestMapper apiRequestMapper;

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

    @Override
    public List<String> getComponentsList(String resourceId) {
        DashboardTemplate dashboardTemplate = dashboardTemplateMapper.selectById(resourceId);
        if (dashboardTemplate == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        String componentsSetting = dashboardTemplate.getComponentsSetting();
        List<String> componentsIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(componentsSetting)) {
            componentsIdList.addAll(JsonUtil.toJsonObjectArray(componentsSetting, String.class));
        }
        return componentsIdList;
    }

    @Override
    public String getComponentsEchartsData(ComponentsDataReq componentsDataReq) {
        String dataSourceComponent = componentsDataReq.getDataSourceComponent();
        if (StringUtils.isEmpty(dataSourceComponent)) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }
        JSONObject jsonObject = JSONObject.parseObject(dataSourceComponent);
        String dataSource = jsonObject.getString("dataSource");
        JSONArray echartsData = getEchartsData(dataSource, componentsDataReq);
        return echartsData.toString();
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

    private JSONArray getEchartsData(String componentSetting, ComponentsDataReq componentsDataReq) {
        if (StringUtils.isNotBlank(componentSetting)) {
            JSONObject jsonObject = JSONObject.parseObject(componentSetting);
            Integer dataType = jsonObject.getInteger("dataType");
            //处理api接口获取数据
            if (Objects.equals(0, dataType)) {
                String url = jsonObject.getString("requestUrl");
                String header = jsonObject.getString("requestHeaders");
                String params = jsonObject.getString("requestParams");
                String method = jsonObject.getString("requestMethod");
                String response = jsonObject.getString("requestResponse");
                ApiConnectReq apiConnectReq = new ApiConnectReq();
                apiConnectReq.setRequestUrl(url);
                apiConnectReq.setRequestMethod(method);
                //设置请求头
                if (StringUtils.isNotBlank(header)) {
                    List<ApiRequestHeaders> apiRequestHeaders = JsonUtil.toJsonObjectArray(header, ApiRequestHeaders.class);
                    apiConnectReq.setRequestHeaders(apiRequestHeaders);
                }
                //设置请求参数
                if (StringUtils.isNotBlank(params)) {
                    List<ApiRequestParams> apiRequestParams = JsonUtil.toJsonObjectArray(params, ApiRequestParams.class);
                    parseRequestParams(apiRequestParams, componentsDataReq.getCustomParams());
                    apiConnectReq.setRequestParams(apiRequestParams);
                }
                String resourceData = apiRequestService.apiConnectTest(apiConnectReq);
                JSONArray echartsDataSet = getEchartsDataSet(resourceData, response);
                DashboardTemplateEchartsData dashboardTemplateEchartsData = new DashboardTemplateEchartsData();
                dashboardTemplateEchartsData.setRows(echartsDataSet);
                return echartsDataSet;
            }
            //TODO处理其它数据源类型
        }
        return null;
    }

    private JSONArray getEchartsDataSet(String resourceData, String response) {
        JSONObject jsonObject = JSONObject.parseObject(resourceData);
        try {
            JSONArray data = jsonObject.getJSONArray("data");
            List<ApiResponseParams> apiResponseParams = JsonUtil.toJsonObjectArray(response, ApiResponseParams.class);
            JSONArray finallyDataArray = new JSONArray();
            JSONArray finallyColumnsArray = new JSONArray();
            Boolean addStatus = Boolean.TRUE;
            if (CollectionUtils.isNotEmpty(apiResponseParams)) {
                for (Object datum : data) {
                    JSONObject finallyObject = new JSONObject();
                    for (ApiResponseParams apiResponseParam : apiResponseParams) {
                        String name = apiResponseParam.getName();
                        Object description = apiResponseParam.getDescription();
                        JSONObject datumObject = JsonUtil.java2JsonObject(datum);
                        Object finallyData = datumObject.get(name);
                        Field declaredField = datum.getClass().getDeclaredField(name);
                        finallyObject.put(description.toString(), finallyData);
                        if (addStatus) {
                            JSONObject columnsObject = new JSONObject();
                            columnsObject.put("name",name);
                            columnsObject.put("friendly_name", description);
                            columnsObject.put("type", declaredField.getType().toString());
                            finallyColumnsArray.add(columnsObject);
                        }
                    }
                    addStatus = Boolean.FALSE;
                    finallyDataArray.add(finallyObject);
                }
            }
            return finallyDataArray;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
        }
    }

    private void parseRequestParams(List<ApiRequestParams> apiRequestParams, String customParams) {
        if (StringUtils.isNotBlank(customParams)) {
            JSONObject jsonObject = JSONObject.parseObject(customParams);
            for (ApiRequestParams apiRequestParam : apiRequestParams) {
                String value = jsonObject.getString(apiRequestParam.getDescription());
                if (StringUtils.isNotBlank(value)) {
                    apiRequestParam.setValue(value);
                }
            }
        }
    }
}
