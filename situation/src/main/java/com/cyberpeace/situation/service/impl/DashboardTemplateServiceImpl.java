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
import com.cyberpeace.situation.controller.res.DashboardTemplatePageRes;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.dao.entity.*;
import com.cyberpeace.situation.dao.mapper.*;
import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.ApiRequestService;
import com.cyberpeace.situation.service.DashboardTemplateService;
import com.cyberpeace.situation.service.DataSourceService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private SituationCategoryMapper situationCategoryMapper;

    @Override
    public String createDashboardTemplate(DashBoardTemplateReq dashBoardTemplateReq) {
        //校验模板名称
        checkTemplateName(dashBoardTemplateReq.getName(), null);

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
        //校验模板名称
        checkTemplateName(dashBoardTemplateReq.getName(), dashBoardTemplateReq.getResourceId());

        String resourceId = dashBoardTemplateReq.getResourceId();
        DashboardTemplate dashboardTemplate = dashboardTemplateMapper.selectById(resourceId);
        if (dashboardTemplate == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        JSONArray newComponentsList = new JSONArray();
        //获取旧组件id
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
        //删除旧组件信息，插入新组件
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
    public DashboardTemplateRes getDashboardTemplateInfo(String resourceId, String type) {
        DashboardTemplate dashboardTemplate = dashboardTemplateMapper.selectById(resourceId);
        if (dashboardTemplate == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        String componentsSetting = dashboardTemplate.getComponentsSetting();
        String viewAngle = dashboardTemplate.getViewAngle();
        String layerShow = dashboardTemplate.getLayerShow();
        List<String> list = JsonUtil.toJsonObjectArray(componentsSetting, String.class);
        DashboardTemplateRes dashboardTemplateRes = BeanUtils.copy(dashboardTemplate, DashboardTemplateRes.class);
        if (StringUtils.isNotBlank(viewAngle)) {
            dashboardTemplateRes.setViewAngle(JSONObject.parseObject(viewAngle, ViewAngle.class));
        }
        if (StringUtils.isNotBlank(layerShow)) {
            dashboardTemplateRes.setLayerShow(JsonUtil.toJsonObjectArray(layerShow, LayerShowReq.class));
        }
        if (CollectionUtils.isNotEmpty(list)) {
            List<DashboardComponents> dashboardComponents = dashboardComponentsMapper.selectBatchIds(list);
            //按图层进行排序
            List<DashboardComponents> dashboardComponentsList =
                    dashboardComponents.stream().sorted(Comparator.comparing(DashboardComponents::getComponentIndex)).collect(Collectors.toList());

            //GIS资源放首位
            List<DashboardComponents> sortDashboardComponentsList = sortDashboardComponents(dashboardComponentsList);

            if (type == null) {
                dashboardTemplateRes.setComponentsSetting(sortDashboardComponentsList);
            } else {
                //存在视角时，按视角类型筛选数据
                List<DashboardComponents> typeDashboardComponents = new ArrayList<>();
                sortDashboardComponentsList.forEach(dashboardComponent -> {
                    String componentType = dashboardComponent.getType();
                    if (Objects.equals(componentType, type)) {
                        typeDashboardComponents.add(dashboardComponent);
                    }
                });
                dashboardTemplateRes.setComponentsSetting(typeDashboardComponents);
            }
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
    public JSONArray getComponentsEchartsData(ComponentsDataReq componentsDataReq) {
        String dataSourceComponent = componentsDataReq.getDataSourceComponent();
        if (StringUtils.isEmpty(dataSourceComponent)) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }
        JSONObject jsonObject = JSONObject.parseObject(dataSourceComponent);
        String dataSource = jsonObject.getString("dataSource");
        JSONArray echartsData = getEchartsData(dataSource, componentsDataReq);
        return echartsData;
    }

    @Override
    public JSONArray getEchartsOption(String componentsSetting, String customParams) {
        ComponentsDataReq componentsDataReq = changeToComponentsDataReq(componentsSetting, customParams);
        return this.getComponentsEchartsData(componentsDataReq);
    }

    private DashboardTemplate changeToDashboardTemplate(DashBoardTemplateReq dashBoardTemplateReq) {
        DashboardTemplate dashboardTemplate = BeanUtils.copy(dashBoardTemplateReq, DashboardTemplate.class);
        JSONArray jsonArray = new JSONArray();
        List<DashboardComponentsReq> componentsSetting = dashBoardTemplateReq.getComponentsSetting();
        List<LayerShowReq> layerShow = dashBoardTemplateReq.getLayerShow();
        ViewAngle viewAngle = dashBoardTemplateReq.getViewAngle();
        if (CollectionUtils.isNotEmpty(componentsSetting)) {
            jsonArray.addAll(componentsSetting);
        }
        dashboardTemplate.setComponentsSetting(jsonArray.toString());
        if (viewAngle != null) {
            dashboardTemplate.setViewAngle(JSONObject.toJSONString(viewAngle));
        }

        if (CollectionUtils.isNotEmpty(layerShow)) {
            JSONArray layerShowArray = new JSONArray();
            layerShowArray.addAll(layerShow);
            dashboardTemplate.setLayerShow(layerShowArray.toString());
        }

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
                return parseApiData(jsonObject, componentsDataReq);
            } else if (Objects.equals(1, dataType)) {
                return parseDbData(jsonObject, componentsDataReq);
            }
            //TODO处理其它数据源类型
        }
        return null;
    }

    /**
     * 处理echarts的dataset数据集结构
     *
     * @param jsonArray
     * @param componentsDataReq
     * @return
     */
    private JSONArray getEchartsDataSet(JSONArray jsonArray, ComponentsDataReq componentsDataReq) {
        JSONArray data = new JSONArray();
        String dimension = componentsDataReq.getDimension();
        String measure = componentsDataReq.getMeasure();
        String groups = componentsDataReq.getGroups();
        if (StringUtils.isBlank(dimension) && StringUtils.isBlank(measure)) {
            return jsonArray;
        }
        try {
            //获取分组数据集和维度数据集
            HashSet<String> groupsSet = new HashSet<>();
            HashSet<String> dimensionSet = new HashSet<>();
            List<JSONObject> dataJsonList = new ArrayList<>();

            for (Object datum : jsonArray) {
                JSONObject datumObject = JsonUtil.java2JsonObject(datum);
                String dimensionValue = datumObject.getString(dimension);
                dimensionSet.add(dimensionValue);
                if (StringUtils.isNotBlank(groups)) {
                    String groupValue = datumObject.getString(groups);
                    groupsSet.add(groupValue);
                }
                dataJsonList.add(datumObject);
            }

            //处理维度和度量数据集
            List<String> measureList = new ArrayList<>();
            if (StringUtils.isNotBlank(measure)) {
                measureList.addAll(Arrays.asList(measure.split(",")));
            }
            for (JSONObject jsonObject : dataJsonList) {
                JSONObject finallyObject = new JSONObject(new LinkedHashMap());
                if (StringUtils.isNotBlank(dimension)) {
                    String string = jsonObject.getString(dimension);
                    finallyObject.put(dimension, string);
                }
                if (CollectionUtils.isNotEmpty(measureList)) {
                    measureList.forEach(measureKey -> {
                        String string = jsonObject.getString(measureKey);
                        finallyObject.put(measureKey, string);
                    });
                }
                if (StringUtils.isNotBlank(groups)) {
                    String string = jsonObject.getString(groups);
                    finallyObject.put(groups, string);
                }
                data.add(finallyObject);
            }

            //分组数据处理
            if (CollectionUtils.isNotEmpty(groupsSet)) {
                JSONArray groupJsonArray = new JSONArray();
                for (String dimensionKey : dimensionSet) {
                    //根据维度筛选分组数据
                    List<JSONObject> groupDataList = new ArrayList<>();
                    dataJsonList.forEach(dataObject -> {
                        String dimensionValue = dataObject.getString(dimension);
                        if (Objects.equals(dimensionValue, dimensionKey)) {
                            groupDataList.add(dataObject);
                        }
                    });

                    JSONObject jsonObject = new JSONObject(new LinkedHashMap());
                    jsonObject.put(dimension, dimensionKey);

                    //处理当前维度的分组数据
                    for (String groupKey : groupsSet) {
                        groupDataList.forEach(groupData -> {
                            String groupValue = groupData.getString(groups);
                            if (Objects.equals(groupKey, groupValue)) {
                                // 有分组情况，度量key只能存在一个值
                                String measureKey = measureList.get(0);
                                String measureVale = groupData.getString(measureKey);
                                jsonObject.put(groupKey, measureVale);
                            }
                        });
                    }
                    groupJsonArray.add(jsonObject);
                }
                return groupJsonArray;
            } else {
                return data;
            }

        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
        }
    }

    /**
     * 映射请求参数集
     *
     * @param apiRequestParams
     * @param customParams
     */
    private void parseRequestParams(List<ApiRequestParams> apiRequestParams, String customParams, String requestMapping) {
        if (CollectionUtils.isNotEmpty(apiRequestParams)) {
            apiRequestParams.clear();
        }

        List<ApiRequestMapping> apiRequestMappings = new ArrayList<>(JsonUtil.toJsonObjectArray(requestMapping, ApiRequestMapping.class));
        //将自定义参数映射到body中
        List<CustomParamReq> customParamReqs = JsonUtil.toJsonObjectArray(customParams, CustomParamReq.class);
        apiRequestMappings.forEach(apiRequestMapping -> {
            ApiRequestParams requestParams = BeanUtils.copy(apiRequestMapping, ApiRequestParams.class);
            if (StringUtils.isNotBlank(apiRequestMapping.getType()) && Objects.equals("constant", apiRequestMapping.getType())) {
                String value = apiRequestMapping.getValue();
                customParamReqs.forEach(customParamReq -> {
                    if (customParamReq != null && customParamReq.getName().equals(value)) {
                        String paramReqValue = customParamReq.getValue();
                        requestParams.setName(value);
                        requestParams.setValue(paramReqValue);
                    }
                });
            }
            apiRequestParams.add(requestParams);
        });
    }

    /**
     * 映射结果参数集
     *
     * @param resourceData
     * @param response
     * @return
     */
    private JSONArray parseResponseParams(String resourceData, String response, String responseKey) {
        JSONArray data = new JSONArray();
        // 处理接口调用返回结果
        List<ApiResponseParams> apiResponseParams;
        try {
            JSONObject jsonObject = JSONObject.parseObject(resourceData);
            Object dataObject = jsonObject.get("data");
            JSONArray jsonArray = new JSONArray();
            //仅返回类型为JSON对象或者JSON数据类型时处理结果
            if (dataObject instanceof JSONObject) {
                JSONObject objectData = jsonObject.getJSONObject("data");
                jsonArray.addAll(objectData.getJSONArray(responseKey));
            }else if (dataObject instanceof JSONArray) {
                jsonArray.addAll(jsonObject.getJSONArray("data"));
            }
            if (!jsonArray.isEmpty()) {
                data.addAll(jsonArray);
            }
            // 获取需要的参数列表
            apiResponseParams = JsonUtil.toJsonObjectArray(response, ApiResponseParams.class);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
        }
        // 筛选参数列表中包含的数据
        if (CollectionUtils.isNotEmpty(apiResponseParams)) {
            JSONArray parseArray = new JSONArray();
            for (Object datum : data) {
                JSONObject finallyObject = new JSONObject(new LinkedHashMap());
                for (ApiResponseParams apiResponseParam : apiResponseParams) {
                    String name = apiResponseParam.getName();
                    JSONObject datumObject = JsonUtil.java2JsonObject(datum);
                    Object finallyData = datumObject.get(name);
                    if (finallyData != null) {
                        finallyObject.put(name, finallyData);
                    }
                }
                parseArray.add(finallyObject);
            }
            return parseArray;
        } else {
            // 若参数列表为空，返回全部数据
            return data;
        }
    }

    private ComponentsDataReq changeToComponentsDataReq(String componentsData, String customParams) {
        try {
            ComponentsDataReq componentsDataReq = new ComponentsDataReq();
            JSONObject componentsDataJson = JSONObject.parseObject(componentsData);
            String dimension = componentsDataJson.getString("dimension");
            String measure = componentsDataJson.getString("measure");
            componentsDataReq.setDataSourceComponent(componentsData);
            componentsDataReq.setDimension(dimension);
            componentsDataReq.setMeasure(measure);
            componentsDataReq.setCustomParams(customParams);
            return componentsDataReq;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
        }
    }

    private JSONArray parseApiData(JSONObject jsonObject, ComponentsDataReq componentsDataReq) {
        String url = jsonObject.getString("requestUrl");
        String header = jsonObject.getString("requestHeaders");
        String params = jsonObject.getString("requestParams");
        String method = jsonObject.getString("requestMethod");
        String response = jsonObject.getString("requestResponse");
        String responseKey = jsonObject.getString("responseKey");
        String requestMapping = jsonObject.getString("requestMapping");
        ApiConnectReq apiConnectReq = new ApiConnectReq();
        apiConnectReq.setRequestUrl(url);
        apiConnectReq.setRequestMethod(method);
        apiConnectReq.setResponseKey(responseKey);

        try {
            //设置请求头
            if (StringUtils.isNotBlank(header)) {
                List<ApiRequestHeaders> apiRequestHeaders = JsonUtil.toJsonObjectArray(header, ApiRequestHeaders.class);
                apiConnectReq.setRequestHeaders(apiRequestHeaders);
            }
            //设置请求参数
            if (StringUtils.isNotBlank(params)) {
                List<ApiRequestParams> apiRequestParams = JsonUtil.toJsonObjectArray(params, ApiRequestParams.class);
                parseRequestParams(apiRequestParams, componentsDataReq.getCustomParams(), requestMapping);
                apiConnectReq.setRequestParams(apiRequestParams);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
        }
        //调用api接口
        String resourceData = apiRequestService.apiConnectTest(apiConnectReq);

        //获取映射结果数据集
        JSONArray jsonArray = parseResponseParams(resourceData, response, responseKey);
        JSONArray echartsDataSet;
        try {
            echartsDataSet = getEchartsDataSet(jsonArray, componentsDataReq);
            return echartsDataSet;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
        }
    }

    private JSONArray parseDbData(JSONObject jsonObject, ComponentsDataReq componentsDataReq) {
        String sql = jsonObject.getString("sqlContent");
        String resourceId = jsonObject.getString("databaseSource");
        String dbName = jsonObject.getString("database");
        String tableName = jsonObject.getString("databaseTable");
        String customParams = jsonObject.getString("condition");
        String requestCustomParams = componentsDataReq.getCustomParams();
        String newCustomParams = parseDbCustomParams(customParams, requestCustomParams);
        DataSourceReq dataSourceReq = new DataSourceReq(sql, resourceId, dbName, tableName, newCustomParams);
        //获取数据库数据
        List<JSONObject> dataSourceData = dataSourceService.getDataSourceData(dataSourceReq);

        JSONArray jsonArray = JsonUtil.stringToArray(dataSourceData.toString());

        return getEchartsDataSet(jsonArray, componentsDataReq);
    }

    private String parseDbCustomParams(String customParams, String requestCustomParams) {
        List<CustomParamReq> customParamReqs = JsonUtil.toJsonObjectArray(requestCustomParams, CustomParamReq.class);
        try {
            JSONArray customParamsArray = JsonUtil.stringToArray(customParams);
            JSONArray jsonArray = new JSONArray();
            for (Object o : customParamsArray) {
                JSONObject customObject = new JSONObject();
                JSONObject datumObject = JsonUtil.java2JsonObject(o);
                String type = datumObject.getString("type");
                String value = datumObject.getString("value");
                String key = datumObject.getString("symbol");
                String name = datumObject.getString("name");
                if (!Objects.equals(type, "constant")) {
                    customParamReqs.forEach(customParamReq -> {
                        if (customParamReq != null && customParamReq.getName().equals(value)) {
                            String paramReqValue = customParamReq.getValue();
                            customObject.put("name", name);
                            customObject.put("value", paramReqValue);
                            customObject.put("symbol", key);
                            customObject.put("type", type);
                        }
                    });
                }
                jsonArray.add(customObject);
            }
            return jsonArray.toString();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
        }
    }

    public List<DashboardComponents> sortDashboardComponents(List<DashboardComponents> dashboardComponentsList){
        List<DashboardComponents> gisDashboardComponentList = new ArrayList<>();
        List<DashboardComponents> commonDashboardComponentList = new ArrayList<>();
        for (DashboardComponents dashboardComponents : dashboardComponentsList) {
            String componentSetting = dashboardComponents.getComponentSetting();
            String type = dashboardComponents.getType();
            JSONObject jsonObject = JSONObject.parseObject(componentSetting);
            String componentType = jsonObject.getString("component_type");
            if (componentType != null && (Objects.equals("0", componentType) || Objects.equals("1", componentType))) {
                gisDashboardComponentList.add(dashboardComponents);
            }
            if (StringUtils.isNotBlank(type) && Objects.equals(type, "all")){
                commonDashboardComponentList.add(dashboardComponents);
            }
        }
        dashboardComponentsList.removeAll(gisDashboardComponentList);
        dashboardComponentsList.removeAll(commonDashboardComponentList);
        List<DashboardComponents> newDashboardComponents = new ArrayList<>();
        //GIS或者拓扑资源排首位
        newDashboardComponents.addAll(gisDashboardComponentList);
        //通用控件或二维组件
        newDashboardComponents.addAll(dashboardComponentsList);
        //全局控件
        newDashboardComponents.addAll(commonDashboardComponentList);

        return newDashboardComponents;
    }

    private void checkTemplateName(String name, String resourceId){
        String oldResourceId = dashboardTemplateMapper.selectByNameString(name);
        if (StringUtils.isNotBlank(oldResourceId)) {
            if ((StringUtils.isNotBlank(resourceId) && !Objects.equals(oldResourceId, resourceId)) || StringUtils.isBlank(resourceId)) {
                throw new BusinessException(ErrorCode.NAME_DUPLICATE);
            }
        }
    }
    
    @Override
    public String getDefaultTemplateId(String taskType){
        SituationCategory situationCategory = situationCategoryMapper.selectByTaskType(taskType);

        return dashboardTemplateMapper.selectByType(situationCategory.getResourceId());
    }
}
