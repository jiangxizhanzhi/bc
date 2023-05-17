package com.cyberpeace.situation.service.impl;

import java.lang.reflect.Field;
import java.util.*;
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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.DashboardTemplatePageRes;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.dao.entity.*;
import com.cyberpeace.situation.dao.mapper.DashboardComponentsMapper;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateMapper;
import com.cyberpeace.situation.dao.mapper.SituationCategoryMapper;
import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.ApiRequestService;
import com.cyberpeace.situation.service.DashboardTemplateService;
import com.cyberpeace.situation.service.DataSourceService;
import com.google.common.collect.Lists;

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
	private DataSourceService dataSourceService;

	@Autowired
	private SituationCategoryMapper situationCategoryMapper;

	// 获取模板数据
	public DashboardTemplate selectDashboardTemplateById(String templateId, Boolean throwNull) {
		DashboardTemplate dashboardTemplate = dashboardTemplateMapper.selectById(templateId);
		if (dashboardTemplate == null && throwNull) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return dashboardTemplate;
	}

	// 转换大屏组件配置（字符串 -> 列表）
	public List<DashboardComponents> convertComponents(String components, String type) {

		// 1、构建返回结构
		List<DashboardComponents> dashboardComponents = Lists.newArrayList();

		// 2、提取组件ID列表
		List<String> componentIds = JsonUtil.toJsonObjectArray(components, String.class);

		// 3、处理组件数据
		// 3-1 如果组件ID列表为空，直接返回空列表
		if (CollectionUtils.isEmpty(componentIds)) {
			return dashboardComponents;
		}

		// 3-2 组件ID列表不为空，提取控件数据
		dashboardComponents = dashboardComponentsMapper.selectBatchIds(componentIds);
		List<DashboardComponents> sortDashboardComponents = new ArrayList<>();
		// 3-3 根据type参数，过滤不同视角下的组件
		if (type == null) {
			// 3-3-1 如果未指定视角，默认返回全部组件
			sortDashboardComponents.addAll(dashboardComponents);
		} else {
			// 3-3-2 存在视角时，按视角类型筛选数据
			dashboardComponents.forEach(dashboardComponent -> {
				String componentType = dashboardComponent.getType();
				// 3-3-2-1 过滤当前视角类型控件及全局控件
				if (Objects.equals(componentType, type) || "all".equals(type)) {
					sortDashboardComponents.add(dashboardComponent);
				}
			});
		}

		// 3-4 处理控件顺序
		return sortDashboardComponents.stream()
				.sorted(Comparator.comparing(DashboardComponents::getComponentIndex).reversed())
				.collect(Collectors.toList());
	}

	@Override
	public String createDashboardTemplate(DashBoardTemplateReq dashBoardTemplateReq) {

		// 1、如果模板ID不为空，更新模板并返回模板ID
		String templateReqResourceId = dashBoardTemplateReq.getResourceId();
		if (StringUtils.isNotBlank(templateReqResourceId)) {
			this.updateDashboardTemplate(dashBoardTemplateReq);
			return templateReqResourceId;
		}

		// 2、如果模板ID为空，创建模板并返回模板ID
		// 2.1、校验模板名称
		checkTemplateName(dashBoardTemplateReq.getName(), null);

		// 2.2、根据图层配置对组件配置进行排序
		sortComponentByLayer(dashBoardTemplateReq.getComponentsSetting(),
				dashBoardTemplateReq.getLayerShow());

		// 2.3、转换配置数据为模板实体
		DashboardTemplate dashboardTemplate = convertTemplate(dashBoardTemplateReq);

		// 2.4、构造模板ID
		dashboardTemplate.setResourceId(IDGeneratorUtil.getID());

		// 2.5、构造组件ID列表
		String componentsSetting = dashboardTemplate.getComponentsSetting();
		JSONArray dashboardComponentsIdList = new JSONArray();
		List<DashboardComponents> dashboardComponentsList = JsonUtil
				.toJsonObjectArray(componentsSetting, DashboardComponents.class);

		for (DashboardComponents dashboardComponents : dashboardComponentsList) {
			String resourceId = IDGeneratorUtil.getID();
			dashboardComponents.setResourceId(resourceId);
			dashboardComponentsIdList.add(resourceId);
		}

		// 写入大屏配置
		dashboardTemplate.setConfig(JsonUtil.toJsonString(dashBoardTemplateReq.getConfig()));
		dashboardTemplate.setComponentsSetting(dashboardComponentsIdList.toString());
		insertTemplate(dashboardTemplate, dashboardComponentsList);

		return dashboardTemplate.getResourceId();
	}

	private void insertTemplate(DashboardTemplate dashboardTemplate,
								List<DashboardComponents> dashboardComponentsList) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				// 写入大屏模板
				dashboardTemplateMapper.insert(dashboardTemplate);

				// 如果组件列表不为空，写入组件数据
				if (CollectionUtils.isNotEmpty(dashboardComponentsList)) {
					dashboardComponentsMapper.batchInsert(dashboardComponentsList);
				}
			}
		});
	}

	@Override
	public String copyDashboardTemplate(String templateId, String name) {

		// 1、校验模板名称
		checkTemplateName(name, null);

		// 2、获取模板数据并构造新增模板数据
		DashboardTemplate dashboardTemplate = selectDashboardTemplateById(templateId, true);

		// 3、清空原有模板ID并赋予新的模板名称
		String newTemplateId = IDGeneratorUtil.getID();
		dashboardTemplate.setId(null);
		dashboardTemplate.setResourceId(newTemplateId);
		dashboardTemplate.setName(name);

		// 4、复制组件数据
		List<String> componentIds = JsonUtil
				.toJsonObjectArray(dashboardTemplate.getComponentsSetting(), String.class);
		List<DashboardComponents> dashboardComponentsList = dashboardComponentsMapper
				.selectBatchIds(componentIds);

		// 4.1、如果组件ID列表和组件列表的大小不一致，抛出异常
		if (componentIds.size() != dashboardComponentsList.size()) {
			throw new BusinessException(SituationErrorCode.TEMPLATE_DATA_ERROR);
		}

		// 4.2、构造组件列表数据
		for (DashboardComponents dashboardComponents : dashboardComponentsList) {
			String resourceId = IDGeneratorUtil.getID();
			// 替换组件ID列表中的原有组件ID
			componentIds.set(componentIds.indexOf(dashboardComponents.getResourceId()), resourceId);
			dashboardComponents.setResourceId(resourceId);
		}

		// 5、重写组件ID列表
		dashboardTemplate.setComponentsSetting(JsonUtil.toJsonString(componentIds));

		// 6、写入模板数据
		insertTemplate(dashboardTemplate, dashboardComponentsList);

		return newTemplateId;

	}

	@Override
	public String updateDashboardTemplate(DashBoardTemplateReq dashBoardTemplateReq) {
		// 校验模板名称
		checkTemplateName(dashBoardTemplateReq.getName(), dashBoardTemplateReq.getResourceId());

		String resourceId = dashBoardTemplateReq.getResourceId();
		DashboardTemplate dashboardTemplate = selectDashboardTemplateById(resourceId, true);

		JSONArray newComponentsList = new JSONArray();
		// 获取旧组件id
		List<String> oldComponentsList = JsonUtil
				.toJsonObjectArray(dashboardTemplate.getComponentsSetting(), String.class);

		List<DashboardComponentsReq> componentsSetting = dashBoardTemplateReq
				.getComponentsSetting();
		List<LayerShowReq> layerShow = dashBoardTemplateReq.getLayerShow();
		if (CollectionUtils.isNotEmpty(componentsSetting)
				&& CollectionUtils.isNotEmpty(layerShow)) {
			List<DashboardComponentsReq> sortComponentsSettingList = sortComponentByLayer(
					componentsSetting, layerShow);
			for (DashboardComponentsReq dashboardComponentsReq : sortComponentsSettingList) {
				String newResourceId = IDGeneratorUtil.getID();
				newComponentsList.add(newResourceId);
				dashboardComponentsReq.setResourceId(newResourceId);
			}
		}

		DashboardTemplate toDashboardTemplate = convertTemplate(dashBoardTemplateReq);
		toDashboardTemplate.setComponentsSetting(newComponentsList.toString());
		List<DashboardComponents> dashboardComponentsList = changeToDashboardComponents(
				componentsSetting);

		// 更新大屏配置
		toDashboardTemplate.setConfig(JsonUtil.toJsonString(dashBoardTemplateReq.getConfig()));

		// 删除旧组件信息，插入新组件
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
	public DashboardTemplateRes getDashboardTemplateInfo(String resourceId, String type,
														 Boolean isPreview) {
		// 1、获取大屏模板数据
		DashboardTemplate dashboardTemplate = selectDashboardTemplateById(resourceId, true);

		// 2、构造返回模板数据
		DashboardTemplateRes dashboardTemplateRes = BeanUtils.copy(dashboardTemplate,
				DashboardTemplateRes.class);
		// 2-1 处理组件配置数据
		dashboardTemplateRes.setComponentsSetting(
				convertComponents(dashboardTemplate.getComponentsSetting(), type));

		return dashboardTemplateRes;
	}

	@Override
	public Integer deleteDashboardTemplate(List<String> idList) {
		if (CollectionUtils.isNotEmpty(idList)) {
			List<DashboardTemplate> dashboardTemplates = dashboardTemplateMapper
					.selectBatchIds(idList);
			List<String> dashboardComponentsIdList = new ArrayList<>();
			dashboardTemplates.forEach(dashboardTemplate -> dashboardComponentsIdList
					.addAll(JsonUtil.toJsonObjectArray(dashboardTemplate.getComponentsSetting(),
							String.class)));
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
	public ListRsp<DashboardTemplatePageRes>
	getDashboardTemplatePage(DashboardTemplatePageReq dashboardTemplatePageReq) {
		Page page = dashboardTemplatePageReq.toPageParam();
		return PageUtil
				.getListResult(this.getBaseMapper().selectPage(page, dashboardTemplatePageReq));
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
		String jsonString = componentsDataReq.getJsonString();
		if (StringUtils.isEmpty(dataSourceComponent)) {
			throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
		}
		JSONArray echartsData = null;
		if (jsonString == null) {
			JSONObject jsonObject = JSONObject.parseObject(dataSourceComponent);
			String dataSource = jsonObject.getString("dataSource");
			if (dataSource == null) {
				JSONObject parseObject = JSONObject
						.parseObject(jsonObject.getString("data_source_component"));
				dataSource = parseObject.getString("dataSource");
			}
			echartsData = getEchartsData(dataSource, componentsDataReq);

		} else {
			JSONArray jsonArray = JsonUtil.stringToArray(jsonString);
			echartsData = getEchartsDataSet(jsonArray, componentsDataReq);
		}
		return echartsData;
	}

	@Override
	public JSONArray getEchartsOption(String componentsSetting, String customParams) {
		ComponentsDataReq componentsDataReq = changeToComponentsDataReq(componentsSetting,
				customParams);
		return this.getComponentsEchartsData(componentsDataReq);
	}

	/**
	 * 转换模板配置为模板实体
	 *
	 * @param dashBoardTemplateReq 仪表板模板要求
	 * @return {@code DashboardTemplate}
	 */
	private DashboardTemplate convertTemplate(DashBoardTemplateReq dashBoardTemplateReq) {

		// 1、复制基本信息
		DashboardTemplate dashboardTemplate = BeanUtils.copy(dashBoardTemplateReq,
				DashboardTemplate.class);

		// 2、处理组件配置数据
		JSONArray jsonArray = new JSONArray();
		List<DashboardComponentsReq> componentsSetting = dashBoardTemplateReq
				.getComponentsSetting();
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

	private List<DashboardComponents>
	changeToDashboardComponents(List<DashboardComponentsReq> dashboardComponentsReqList) {
		List<DashboardComponents> dashboardComponentsList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(dashboardComponentsReqList)) {
			for (DashboardComponentsReq dashboardComponentsReq : dashboardComponentsReqList) {
				DashboardComponents dashboardComponents = BeanUtils.deepCopy(dashboardComponentsReq,
						DashboardComponents.class);
				Object componentSetting = dashboardComponentsReq.getComponentSetting();
				Object datasourceSetting = dashboardComponentsReq.getDatasourceSetting();
				if (componentSetting != null) {
					dashboardComponents
							.setComponentSetting(JsonUtil.toJsonString(componentSetting));
				}
				if (datasourceSetting != null) {
					dashboardComponents
							.setDatasourceSetting(JsonUtil.toJsonString(dashboardComponents));
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
			// 处理api接口获取数据
			if (Objects.equals(0, dataType)) {
				return parseApiData(jsonObject, componentsDataReq);
			} else if (Objects.equals(1, dataType)) {
				return parseDbData(jsonObject, componentsDataReq);
			}
			// TODO处理其它数据源类型
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
			// 获取分组数据集和维度数据集
			HashSet<Object> groupsSet = new HashSet<>();
			HashSet<Object> dimensionSet = new HashSet<>();
			List<JSONObject> dataJsonList = new ArrayList<>();

			for (Object datum : jsonArray) {
				JSONObject datumObject = JsonUtil.java2JsonObject(datum);
				Object dimensionValue = datumObject.get(dimension);
				dimensionSet.add(dimensionValue);
				if (StringUtils.isNotBlank(groups)) {
					Object groupValue = datumObject.get(groups);
					groupsSet.add(groupValue);
				}
				dataJsonList.add(datumObject);
			}

			// 处理维度和度量数据集
			List<String> measureList = new ArrayList<>();
			if (StringUtils.isNotBlank(measure)) {
				if(measure.startsWith("[")&&measure.endsWith("]")){
					measureList.addAll(JsonUtil.toJsonObjectArray(measure,String.class));
				}else{
					measureList.addAll(Arrays.asList(measure.split(",")));
				}
			}
			for (JSONObject jsonObject : dataJsonList) {
				JSONObject finallyObject = new JSONObject(new LinkedHashMap());
				if (StringUtils.isNotBlank(dimension)) {
					String string = jsonObject.getString(dimension);
					finallyObject.put(dimension, string);
				}
				if (CollectionUtils.isNotEmpty(measureList)) {
					measureList.forEach(measureKey -> {
						Object string = jsonObject.get(measureKey);
						finallyObject.put(measureKey, string);
					});
				}
				if (StringUtils.isNotBlank(groups)) {
					Object string = jsonObject.get(groups);
					finallyObject.put(groups, string);
				}
				data.add(finallyObject);
			}

			// 分组数据处理
			if (CollectionUtils.isNotEmpty(groupsSet)) {
				JSONArray groupJsonArray = new JSONArray();
				for (Object dimensionKey : dimensionSet) {
					// 根据维度筛选分组数据
					List<JSONObject> groupDataList = new ArrayList<>();
					dataJsonList.forEach(dataObject -> {
						String dimensionValue = dataObject.getString(dimension);
						if (Objects.equals(dimensionValue, dimensionKey)) {
							groupDataList.add(dataObject);
						}
					});

					JSONObject jsonObject = new JSONObject(new LinkedHashMap());
					jsonObject.put(dimension, dimensionKey);

					// 处理当前维度的分组数据
					for (Object groupKey : groupsSet) {
						groupDataList.forEach(groupData -> {
							Object groupValue = groupData.get(groups);
							if (Objects.equals(groupKey, groupValue)) {
								// 有分组情况，度量key只能存在一个值
								String measureKey = measureList.get(0);
								Object measureVale = groupData.get(measureKey);
								jsonObject.put(groupKey.toString(), measureVale);
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
			log.error("json type error!: ", e);
			throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
		}
	}

	/**
	 * 映射请求参数集
	 *
	 * @param apiRequestParams
	 * @param customParams
	 */
	private void parseRequestParams(List<ApiRequestParams> apiRequestParams, String customParams,
									String requestMapping) {
		if (CollectionUtils.isNotEmpty(apiRequestParams)) {
			apiRequestParams.clear();
		}

		List<ApiRequestMapping> apiRequestMappings = new ArrayList<>(
				JsonUtil.toJsonObjectArray(requestMapping, ApiRequestMapping.class));
		// 将自定义参数映射到body中
		List<CustomParamReq> customParamReqs = JsonUtil.toJsonObjectArray(customParams,
				CustomParamReq.class);
		apiRequestMappings.forEach(apiRequestMapping -> {
			ApiRequestParams requestParams = BeanUtils.copy(apiRequestMapping,
					ApiRequestParams.class);
			if (StringUtils.isNotBlank(apiRequestMapping.getType())
					&& Objects.equals("constant", apiRequestMapping.getType())) {
				String value = apiRequestMapping.getName();
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
	private JSONArray parseResponseParams(String resourceData, String response,
										  String responseKey) {
		JSONArray data = new JSONArray();
		// 处理接口调用返回结果
		List<ApiResponseParams> apiResponseParams;
		try {
			JSONObject jsonObject = JSONObject.parseObject(resourceData);
			Object dataObject = jsonObject.get("data");
			JSONArray jsonArray = new JSONArray();
			// 仅返回类型为JSON对象或者JSON数据类型时处理结果
			if (dataObject instanceof JSONObject) {
				JSONObject objectData = jsonObject.getJSONObject("data");
				jsonArray.addAll(objectData.getJSONArray(responseKey));
			} else if (dataObject instanceof JSONArray) {
				jsonArray.addAll(jsonObject.getJSONArray("data"));
			}
			if (!jsonArray.isEmpty()) {
				data.addAll(jsonArray);
			}
			// 获取需要的参数列表
			apiResponseParams = JsonUtil.toJsonObjectArray(response, ApiResponseParams.class);
		} catch (Exception e) {
			log.error("json type error!: ", e);
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

	private ComponentsDataReq changeToComponentsDataReq(String componentsData,
														String customParams) {
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
			// 设置请求头
			if (StringUtils.isNotBlank(header)) {
				List<ApiRequestHeaders> apiRequestHeaders = JsonUtil.toJsonObjectArray(header,
						ApiRequestHeaders.class);
				apiConnectReq.setRequestHeaders(apiRequestHeaders);
			}
			// 设置请求参数
			if (StringUtils.isNotBlank(params)) {
				List<ApiRequestParams> apiRequestParams = JsonUtil.toJsonObjectArray(params,
						ApiRequestParams.class);
				parseRequestParams(apiRequestParams, componentsDataReq.getCustomParams(),
						requestMapping);
				InstanceDataQueryReq instanceDataQueryReq = componentsDataReq
						.getInstanceDataQueryReq();
				if (instanceDataQueryReq != null) {
					parseParam(instanceDataQueryReq, apiRequestParams);
				}
				apiConnectReq.setRequestParams(apiRequestParams);
			}
		} catch (Exception e) {
			log.error("json type error!: ", e);
			throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
		}
		// 调用api接口
		String resourceData = apiRequestService.apiConnectTest(apiConnectReq);

		// 获取映射结果数据集
		JSONArray jsonArray = parseResponseParams(resourceData, response, responseKey);
		JSONArray echartsDataSet;
		try {
			echartsDataSet = getEchartsDataSet(jsonArray, componentsDataReq);
			return echartsDataSet;
		} catch (Exception e) {
			log.error("json type error!: ", e);
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
		InstanceDataQueryReq instanceDataQueryReq = componentsDataReq.getInstanceDataQueryReq();
		String newCustomParams = parseDbCustomParams(customParams, requestCustomParams,
				instanceDataQueryReq);
		DataSourceReq dataSourceReq = new DataSourceReq(sql, resourceId, dbName, tableName,
				newCustomParams);
		// 获取数据库数据
		List<JSONObject> dataSourceData = dataSourceService.getDataSourceData(dataSourceReq);

		JSONArray jsonArray = JsonUtil.stringToArray(dataSourceData.toString());

		return getEchartsDataSet(jsonArray, componentsDataReq);
	}

	private String parseDbCustomParams(String customParams, String requestCustomParams,
									   InstanceDataQueryReq instanceDataQueryReq) {
		List<CustomParamReq> customParamReqs = JsonUtil.toJsonObjectArray(requestCustomParams,
				CustomParamReq.class);
		try {
			JSONArray customParamsArray = JsonUtil.stringToArray(customParams);
			JSONArray jsonArray = new JSONArray();
			for (Object o : customParamsArray) {
				JSONObject customObject = new JSONObject();
				JSONObject datumObject = JsonUtil.java2JsonObject(o);
				String type = datumObject.getString("type");
				String value = datumObject.getString("value");
				String name = datumObject.getString("name");
				if (!Objects.equals(type, "constant")) {
					customParamReqs.forEach(customParamReq -> {
						if (customParamReq != null && customParamReq.getName().equals(value)) {
							String paramReqValue = customParamReq.getValue();
							customObject.put("name", name);
							customObject.put("value", paramReqValue);
						}
					});
				}
				jsonArray.add(customObject);
			}
			if (instanceDataQueryReq != null) {
				parseDbParams(instanceDataQueryReq, jsonArray);
			}
			return jsonArray.toString();
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.UNSUPPORTED_JSON_TYPE);
		}
	}

	/**
	 * 校验模板名称是否存在
	 *
	 * @param name       名称
	 * @param resourceId 模板ID
	 */
	private void checkTemplateName(String name, String resourceId) {

		// 1、通过名称查询已存在模板ID
		String oldResourceId = dashboardTemplateMapper.selectByName(name);

		// 2、如果已存在模板ID为空，或者已存在模板ID不为空，但是与当前模板ID相等，返回null
		if (StringUtils.isBlank(oldResourceId) || Objects.equals(oldResourceId, resourceId)) {
			return;
		}

		// 3、否则，抛出名称重复异常
		throw new BusinessException(ErrorCode.NAME_DUPLICATE);
	}

	@Override
	public String getDefaultTemplateId(String taskType) {
		SituationCategory situationCategory = situationCategoryMapper.selectByTaskType(taskType);
		List<String> templates = dashboardTemplateMapper
				.selectByType(situationCategory.getResourceId());
		if (templates.isEmpty()) {
			return null;
		} else {
			return templates.get(0);
		}
	}

	@Override
	public List<DashboardComponentsReq> sortComponentByLayer(
			List<DashboardComponentsReq> componentsSettingList, List<LayerShowReq> layerShow) {

		List<Integer> sortComponentIndexs = new ArrayList<>();

		// 获取所有有效图层列表
		for (LayerShowReq layerShowReq : layerShow) {
			// 提取组件ID顺序
			if (CollectionUtils.isNotEmpty(layerShowReq.getChildren())) {
				// 如果当前图层下存在子图层，则将子图层的组件ID添加到组件ID列表中
				for (LayerShowReq childLayerShowReq : layerShowReq.getChildren()) {
					sortComponentIndexs.add(childLayerShowReq.getComponentIndex());
				}
			} else {
				// 否则，将当前图层的组件ID添加到组件ID列表中
				sortComponentIndexs.add(layerShowReq.getComponentIndex());
			}
		}

		// 如果图层列表与控件列表长度不一致，抛出异常
		if (sortComponentIndexs.size() != componentsSettingList.size()) {
			throw new BusinessException(SituationErrorCode.LAYER_NUM_ERROR);
		}

		// 根据控件索引值在列表中的位置对控件列表进行排序
		componentsSettingList.sort(Comparator.comparingInt(
				component -> sortComponentIndexs.indexOf(component.getComponentIndex())));

		return componentsSettingList;
	}

	private void parseParam(InstanceDataQueryReq instanceDataQueryReq,
							List<ApiRequestParams> apiRequestParams) {
		try {
			Field[] fields = instanceDataQueryReq.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				// 获取字段属性名称
				String name = field.getName();
				if (!StringUtils.equals(name, "dashboardId")) {
					Object value = field.get(instanceDataQueryReq);
					if (value != null) {
						ApiRequestParams requestParams = new ApiRequestParams();
						requestParams.setName(name);
						requestParams.setValue(value);
						apiRequestParams.add(requestParams);
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void parseDbParams(InstanceDataQueryReq instanceDataQueryReq, JSONArray jsonArray) {
		try {
			Field[] fields = instanceDataQueryReq.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				// 获取字段属性名称
				String name = field.getName();
				if (!StringUtils.equals(name, "dashboardId")) {
					Object value = field.get(instanceDataQueryReq);
					if (value != null) {
						JSONObject customObject = new JSONObject();
						customObject.put("name", name);
						customObject.put("value", value);
						jsonArray.add(customObject);
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JSONArray getComponentsEchartsData(String jsonString) {

		return null;
	}
}
