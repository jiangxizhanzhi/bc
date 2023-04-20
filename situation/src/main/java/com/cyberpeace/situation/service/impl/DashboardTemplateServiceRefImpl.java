package com.cyberpeace.situation.service.impl;

import java.lang.reflect.Field;
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
import com.cyberpeace.situation.controller.req.DashBoardTemplateConfigReq;
import com.cyberpeace.situation.controller.req.DashBoardTemplateReq;
import com.cyberpeace.situation.controller.req.DashboardComponentsReq;
import com.cyberpeace.situation.controller.req.LayerShowReq;
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
import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.DashboardTemplateService;
import com.cyberpeace.situation.service.context.DashboardTemplateContext;
import com.cyberpeace.situation.service.context.DashboardTemplateRefContext;
import com.cyberpeace.situation.service.context.DynamicParameterContext;
import com.cyberpeace.situation.service.context.config.DashboardConfigContext;

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

	public DashboardTemplateRef getDashboardTemplateRefById(String resourceId){
		DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefMapper
				.selectById(resourceId);
		if (dashboardTemplateRef == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return dashboardTemplateRef;
	}

	@Override
	public DashboardTemplateRef createDashboardTemplate(String resourceId) {

		// 获取模板数据
		DashboardTemplateRes dashboardTemplateInfo = dashboardTemplateService
				.getDashboardTemplateInfo(resourceId, null, Boolean.FALSE);
		DashBoardTemplateReq dashBoardTemplateReq = BeanUtils.copy(dashboardTemplateInfo,
				DashBoardTemplateReq.class);
		DashboardTemplateRef dashboardTemplateRef = changeToDashboardTemplateRef(
				dashBoardTemplateReq);

		// 设置备份数据
		dashboardTemplateRef.setResourceId(IDGeneratorUtil.getID());
		JSONArray dashboardComponentsIdList = new JSONArray();
		List<DashboardComponents> dashboardComponentsList = dashboardTemplateInfo
				.getComponentsSetting();

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
		List<DashboardComponentsReq> componentsSetting = dashBoardTemplateReq
				.getComponentsSetting();
		List<LayerShowReq> layerShow = dashBoardTemplateReq.getLayerShow();

		// 资源校验
		DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefMapper
				.selectById(resourceId);
		if (dashboardTemplateRef == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 获取旧数据控件
		JSONArray newComponentsList = new JSONArray();
		List<String> oldComponentsList = JsonUtil
				.toJsonObjectArray(dashboardTemplateRef.getComponentsSetting(), String.class);

		// 获取按图层排序后的控件
		List<DashboardComponentsReq> sortDashboardComponentsReqList = dashboardTemplateService
				.sortComponent(componentsSetting, layerShow);
		dashBoardTemplateReq.setComponentsSetting(sortDashboardComponentsReqList);
		if (CollectionUtils.isNotEmpty(sortDashboardComponentsReqList)) {
			for (DashboardComponentsReq dashboardComponentsReq : sortDashboardComponentsReqList) {
				String newResourceId = IDGeneratorUtil.getID();
				newComponentsList.add(newResourceId);
				dashboardComponentsReq.setResourceId(newResourceId);
			}
		}

		DashboardTemplateRef toDashboardTemplateRef = changeToDashboardTemplateRef(
				dashBoardTemplateReq);
		toDashboardTemplateRef.setComponentsSetting(newComponentsList.toString());
		List<DashboardComponents> dashboardComponentsList = changeToDashboardComponents(
				sortDashboardComponentsReqList);
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
	public DashboardTemplateRes getDashboardTemplateInfo(String resourceId, String type,
			Boolean isPreview) {
		DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefMapper
				.selectById(resourceId);
		if (dashboardTemplateRef == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		String componentsSetting = dashboardTemplateRef.getComponentsSetting();
		String layerShow = dashboardTemplateRef.getLayerShow();

		String viewAngle = dashboardTemplateRef.getViewAngle();
		List<String> list = JsonUtil.toJsonObjectArray(componentsSetting, String.class);
		DashboardTemplateRes dashboardTemplateRes = BeanUtils.copy(dashboardTemplateRef,
				DashboardTemplateRes.class);
		if (StringUtils.isNotBlank(viewAngle)) {
			dashboardTemplateRes.setViewAngle(JSONObject.parseObject(viewAngle, ViewAngle.class));
		}
		if (StringUtils.isNotBlank(layerShow)) {
			dashboardTemplateRes
					.setLayerShow(JsonUtil.toJsonObjectArray(layerShow, LayerShowReq.class));
		}
		// 图层排序
		if (CollectionUtils.isNotEmpty(list)) {
			List<DashboardComponents> dashboardComponents = dashboardComponentsMapper
					.selectBatchIds(list);
			List<DashboardComponents> sourceDashboardComponents = new ArrayList<>();
			List<DashboardComponents> commonDashboardComponentList = new ArrayList<>();
			for (DashboardComponents dashboardComponent : dashboardComponents) {
				// 视角筛选
				String sourceType = dashboardComponent.getType();
				if (type != null && !Objects.equals(sourceType, type)) {
					continue;
				}
				sourceDashboardComponents.add(dashboardComponent);
				if (StringUtils.isNotBlank(type) && Objects.equals(type, "all")) {
					commonDashboardComponentList.add(dashboardComponent);
				}
			}

			// 通用控件处理
			sourceDashboardComponents.removeAll(commonDashboardComponentList);
			List<DashboardComponents> sortDashboardComponent = sourceDashboardComponents.stream()
					.sorted(Comparator.comparing(DashboardComponents::getComponentIndex).reversed())
					.collect(Collectors.toList());
			commonDashboardComponentList.addAll(sortDashboardComponent);

			dashboardTemplateRes.setComponentsSetting(commonDashboardComponentList);

		}
		return dashboardTemplateRes;
	}

	@Override
	public Integer deleteDashboardTemplate(List<String> idList) {
		if (CollectionUtils.isNotEmpty(idList)) {
			List<DashboardTemplateRef> dashboardTemplateRefs = dashboardTemplateRefMapper
					.selectBatchIds(idList);
			List<String> dashboardComponentsIdList = new ArrayList<>();
			dashboardTemplateRefs.forEach(dashboardTemplateRef -> dashboardComponentsIdList
					.addAll(JsonUtil.toJsonObjectArray(dashboardTemplateRef.getComponentsSetting(),
							String.class)));
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

	private DashboardTemplateRef
			changeToDashboardTemplateRef(DashBoardTemplateReq dashBoardTemplateReq) {
		DashboardTemplateRef dashboardTemplateRef = BeanUtils.copy(dashBoardTemplateReq,
				DashboardTemplateRef.class);
		dashboardTemplateRef.setOriginTemplateId(dashBoardTemplateReq.getResourceId());
		JSONArray jsonArray = new JSONArray();
		List<DashboardComponentsReq> componentsSetting = dashBoardTemplateReq
				.getComponentsSetting();
		List<LayerShowReq> layerShow = dashBoardTemplateReq.getLayerShow();
		ViewAngle viewAngle = dashBoardTemplateReq.getViewAngle();
		if (CollectionUtils.isNotEmpty(componentsSetting)) {
			jsonArray.addAll(componentsSetting);
		}
		dashboardTemplateRef.setComponentsSetting(jsonArray.toString());
		if (viewAngle != null) {
			dashboardTemplateRef.setViewAngle(JSONObject.toJSONString(viewAngle));
		}

		if (CollectionUtils.isNotEmpty(layerShow)) {
			JSONArray layerShowArray = new JSONArray();
			layerShowArray.addAll(layerShow);
			dashboardTemplateRef.setLayerShow(layerShowArray.toString());
		}

		// 复制大屏参数配置
		dashboardTemplateRef.setConfig(JsonUtil.toJsonString(dashBoardTemplateReq.getConfig()));

		return dashboardTemplateRef;
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

	@Override
	public List<DashboardComponents> exchangeOptionDataSet(
			SituationPublishInstanceReviewRes situationPublishInstanceReviewRes,
			DynamicParameterContext context) {
		DashboardTemplateRes dashboardTemplateRes = situationPublishInstanceReviewRes
				.getTemplateInfo();
		List<DashboardComponents> componentsSettingList = new ArrayList<>();
		if (dashboardTemplateRes != null) {
			String taskId = situationPublishInstanceReviewRes.getTaskId();
			String subjectId = situationPublishInstanceReviewRes.getSubjectId();
			context.setSubjectId(subjectId);
			context.setTaskId(taskId);
			JSONArray jsonArray = parseParam(context);
			componentsSettingList = dashboardTemplateRes.getComponentsSetting();
			if (CollectionUtils.isNotEmpty(componentsSettingList)) {
				for (DashboardComponents dashboardComponent : componentsSettingList) {
					String componentSetting = dashboardComponent.getComponentSetting();
					JSONObject jsonObject = JSONObject.parseObject(componentSetting);
					String componentType = jsonObject.getString("component_type");
					if (componentType == null) {
						String option = jsonObject.getString("option");
						JSONObject optionObject = JSONObject.parseObject(option);
						JSONObject dataset = optionObject.getJSONObject("dataset");
						JSONArray echartsOption = dashboardTemplateService
								.getEchartsOption(componentSetting, jsonArray.toJSONString());
						Boolean dynamic = null;
						if (dataset != null) {
							dynamic = dataset.getBoolean("dynamic");
						}
						JSONObject dataSetObject = new JSONObject();
						// dataset 数据模型
						if (dynamic == null || dynamic) {
							dataSetObject.put("source", echartsOption);
							optionObject.replace("dataset", dataSetObject);
						} else {
							parseUnDataSet(optionObject, echartsOption, componentSetting);
						}

					}
				}
			}
		}
		return componentsSettingList;
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

	private JSONArray parseParam(DynamicParameterContext context) {
		JSONArray jsonArray = new JSONArray();
		try {
			Field[] fields = context.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				// 获取字段属性名称
				String name = field.getName();
				if (!StringUtils.equals(name, "dashboardId")) {
					Object value = field.get(context);
					if (value != null) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("name", name);
						jsonObject.put("value", value.toString());
						jsonArray.add(jsonObject);
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	private void parseUnDataSet(JSONObject optionObject, JSONArray echartsOption,
			String componentsData) {
		JSONObject xAxis = optionObject.getJSONObject("xAxis");
		JSONObject yAxis = optionObject.getJSONObject("yAxis");
		JSONArray series = optionObject.getJSONArray("series");
		// TODO 其它坐标轴类型，如极坐标

		JSONObject componentsDataJson = JSONObject.parseObject(componentsData);
		String dimension = componentsDataJson.getString("dimension");
		String measure = componentsDataJson.getString("measure");
		String groups = componentsDataJson.getString("groups");

		JSONArray dimensionOptionArray = getOptionValueArray(echartsOption, dimension);
		JSONArray measureOptionArray = getOptionValueArray(echartsOption, measure);
		JSONArray groupsOptionArray = getOptionValueArray(echartsOption, measure);
		// xAxis 存在时，替换data数据
		if (xAxis != null && xAxis.containsKey("data")) {
			xAxis.replace("data", dimensionOptionArray);
		}
		// yAxis 存在时，替换data数据
		if (yAxis != null && yAxis.containsKey("data")) {
			yAxis.replace("data", measureOptionArray);
		}
		// series是否包含data
		if ((xAxis == null && yAxis == null)
				&& JsonUtil.java2JsonObject(series.get(0)).containsKey("data")) {
			for (int i = 0; i < series.size(); i++) {
				JSONObject datumObject = JsonUtil.java2JsonObject(series.get(i));
				JSONArray dataArray = datumObject.getJSONArray("data");
				// data 类型数据为对象类型[{"xxx":xxx,"xxx":"xxx"}]
				try {
					JSONObject jsonObject = JsonUtil.java2JsonObject(dataArray.get(0));
					// 维度度量衡存在时，需要替换series里面的name和value的值，仅在series里面能转成json时
					if ((dimension != null && measure != null) && (dimensionOptionArray.size() >= i
							&& measureOptionArray.size() >= i)) {
						if (jsonObject.containsKey("name")) {
							jsonObject.replace("name", dimensionOptionArray.get(i));
						}
						if (jsonObject.containsKey("value")) {
							jsonObject.replace("value", measureOptionArray.get(i));
						}
					} else if ((dimension != null && measure == null)
							&& (dimensionOptionArray.size() >= i
									&& measureOptionArray.size() >= i)) {
						if (jsonObject.containsKey("name")) {
							jsonObject.replace("name", dimensionOptionArray.get(i));
						}
					}
				} catch (Exception e) {
					// data 类型为[1，2，3，4]或者["a","b","c"]类型
					if (dataArray.get(0) instanceof String || dataArray.get(0) instanceof Integer) {
						datumObject.replace("data", getOptionValueArray(echartsOption, dimension));
					}
				}
				// 存在分组时
				if (groups != null) {
					if (datumObject.containsKey("links") && groupsOptionArray.size() >= i) {
						datumObject.replace("links", groupsOptionArray.get(i));
					}
					if (datumObject.containsKey("categories") && dimensionOptionArray.size() >= i) {
						JSONArray jsonArray = new JSONArray();
						for (Object o : dimensionOptionArray) {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("name", o);
							jsonArray.add(jsonObject);
						}
						datumObject.replace("categories", jsonArray);
					}
				}
			}
		}
	}

	private JSONArray getOptionValueArray(JSONArray echartsOption, String value) {
		JSONArray jsonArray = new JSONArray();
		String[] split = value.split(",");
		if (echartsOption != null) {
			for (Object datum : echartsOption) {
				JSONObject datumObject = JsonUtil.java2JsonObject(datum);
				JSONArray dataArray = new JSONArray();
				for (String splitValue : split) {
					if (datumObject.containsKey(splitValue)) {
						if (split.length == 1) {
							jsonArray.add(datumObject.get(splitValue));
						} else {
							dataArray.add(datumObject.get(splitValue));
							jsonArray.add(dataArray);
						}
					}

				}
			}
		}
		return jsonArray;
	}

	@Override
	public DashboardTemplateRefContext
			updateTemplateRefConfig(DashBoardTemplateConfigReq dashBoardTemplateConfigReq) {

		DashboardTemplateRef dashboardTemplateRef = getDashboardTemplateRefById(
				dashBoardTemplateConfigReq.getResourceId());

		// 修改大屏GIS初始视角
		if (dashBoardTemplateConfigReq.getViewAngle() != null) {
			dashboardTemplateRef
					.setViewAngle(JsonUtil.toJsonString(dashBoardTemplateConfigReq.getViewAngle()));
		}

		DashboardConfigContext dashboardConfigContext = JsonUtil
				.toJsonObject(dashboardTemplateRef.getConfig(), DashboardConfigContext.class);

		// 当大屏实例没有初始配置时，创建初始数据
		if (dashboardConfigContext == null){
			dashboardConfigContext = new DashboardConfigContext();
		}

		// 修改大屏拓扑配置
		if (dashBoardTemplateConfigReq.getTopoConfig() != null) {
			dashboardConfigContext.setTopoConfig(dashBoardTemplateConfigReq.getTopoConfig());
		}

		// 修改大屏GIS配置
		if (dashBoardTemplateConfigReq.getGisConfig() != null) {
			dashboardConfigContext.setGisConfig(dashBoardTemplateConfigReq.getGisConfig());
		}

		dashboardTemplateRef.setConfig(JsonUtil.toJsonString(dashboardConfigContext));
		dashboardTemplateRefMapper.updateById(dashboardTemplateRef);
		return BeanUtils.copy(dashboardTemplateRef, DashboardTemplateRefContext.class);
	}
}
