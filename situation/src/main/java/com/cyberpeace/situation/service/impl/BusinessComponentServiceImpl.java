package com.cyberpeace.situation.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.dao.entity.*;
import com.cyberpeace.situation.dao.mapper.BusinessComponentMapper;
import com.cyberpeace.situation.dao.mapper.BusinessComponentsSettingMapper;
import com.cyberpeace.situation.dao.mapper.SituationCategoryMapper;
import com.cyberpeace.situation.service.ApiRequestService;
import com.cyberpeace.situation.service.BusinessComponentService;
import com.cyberpeace.situation.service.DataSourceService;
import com.cyberpeace.situation.service.context.ExportBusinessComponentContext;
import com.google.common.collect.Lists;

/**
 * 业务组件服务impl
 *
 * @author dell
 * @date 2023/06/20
 */
@Service
public class BusinessComponentServiceImpl extends ServiceImpl<BusinessComponentMapper, BusinessComponent> implements BusinessComponentService {

	@Autowired
	private BusinessComponentMapper businessComponentMapper;

	@Autowired
	private BusinessComponentsSettingMapper businessComponentsSettingMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private ApiRequestService apiRequestService;

	@Autowired
	private DataSourceService dataSourceService;

	@Autowired
	private SituationCategoryMapper situationCategoryMapper;

	/**
	 * 选择业务组件id
	 *
	 * @param templateId 模板id
	 * @param throwNull  把空
	 * @return {@link BusinessComponent}
	 */// 获取业务组件数据
	public BusinessComponent selectBusinessComponentById(String templateId, Boolean throwNull) {
		BusinessComponent BusinessComponent = businessComponentMapper.selectById(templateId);
		if (BusinessComponent == null && throwNull) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return BusinessComponent;
	}

	/**
	 * 转换组件
	 *
	 * @param components 组件
	 * @param type       类型
	 * @return {@link List}<{@link BusinessComponentsSetting}>
	 */// 转换业务组件配置（字符串 -> 列表）
	public List<BusinessComponentsSetting> convertComponents(String components, String type) {

		// 1、构建返回结构
		List<BusinessComponentsSetting> dashboardComponents = Lists.newArrayList();

		// 2、提取组件ID列表
		List<String> componentIds = JsonUtil.toJsonObjectArray(components, String.class);

		// 3、处理组件数据
		// 3-1 如果组件ID列表为空，直接返回空列表
		if (CollectionUtils.isEmpty(componentIds)) {
			return dashboardComponents;
		}

		// 3-2 组件ID列表不为空，提取控件数据
		dashboardComponents = businessComponentsSettingMapper.selectBatchIds(componentIds);
		List<BusinessComponentsSetting> sortBusinessComponentsSetting = new ArrayList<>();

 		dashboardComponents.forEach(dashboardComponent -> {
			dashboardComponent.setComponentSettingObject(JSONObject.parseObject(dashboardComponent.getComponentSetting()));
  			sortBusinessComponentsSetting.add(dashboardComponent);

		});

		// 3-3 返回组件信息
		return sortBusinessComponentsSetting;
	}

	/**
	 * 创建业务组件
	 *
	 * @param businessComponentReq 业务组件请求
	 * @return {@link String}
	 */
	@Override
	public String createBusinessComponent(BusinessComponentReq businessComponentReq) {

		// 1、如果业务组件ID不为空，更新业务组件并返回业务组件ID
		String templateReqResourceId = businessComponentReq.getResourceId();
		if (StringUtils.isNotBlank(templateReqResourceId)) {
			this.updateBusinessComponent(businessComponentReq);
			return templateReqResourceId;
		}

		// 2、如果业务组件ID为空，创建业务组件并返回业务组件ID
		// 2.1、校验业务组件名称
		checkTemplateName(businessComponentReq.getName(), null);

		// 2.2、根据图层配置对组件配置进行排序
		// sortComponentByLayer(businessComponentReq.getBusinessComponentsSettings(),
		// businessComponentReq.getLayerShow());

		// 2.3、转换配置数据为业务组件实体
		BusinessComponent businessComponent = convertComponent(businessComponentReq);

		// 2.4、构造业务组件ID
		businessComponent.setResourceId(IDGeneratorUtil.getID());

		// 2.5、构造组件ID列表
		String componentsSetting = businessComponent.getComponentsSetting();
		JSONArray dashboardComponentsIdList = new JSONArray();
		List<BusinessComponentsSetting> dashboardComponentsList = JsonUtil
				.toJsonObjectArray(componentsSetting, BusinessComponentsSetting.class);

		for (BusinessComponentsSetting dashboardComponents : dashboardComponentsList) {
			String resourceId = IDGeneratorUtil.getID();
			dashboardComponents.setResourceId(resourceId);
			dashboardComponentsIdList.add(resourceId);
		}

		// 写入组件配置
		businessComponent.setComponentsSetting(dashboardComponentsIdList.toString());
		insertTemplate(businessComponent, dashboardComponentsList);

		return businessComponent.getResourceId();
	}

	private void insertTemplate(BusinessComponent businessComponent,
			List<BusinessComponentsSetting> dashboardComponentsList) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				// 写入业务组件
				businessComponentMapper.insert(businessComponent);

				// 如果组件列表不为空，写入组件数据
				if (CollectionUtils.isNotEmpty(dashboardComponentsList)) {
					businessComponentsSettingMapper.batchInsert(dashboardComponentsList);
				}
			}
		});
	}

	/**
	 * 导出业务组件
	 *
	 * @param tempDir    临时dir
	 * @param templateId 模板id
	 * @return {@link String}
	 * @throws IOException ioexception
	 */
	@Override
	public String exportBusinessComponent(String tempDir, String templateId) throws IOException {

		// 1、获取业务组件数据
		BusinessComponent businessComponent = selectBusinessComponentById(templateId, true);

		// 2、清空原有业务组件ID并赋予新的业务组件名称
		businessComponent.setId(null);
		businessComponent.setResourceId(IDGeneratorUtil.getID());

		// 3、复制组件数据
		List<String> componentIds = JsonUtil
				.toJsonObjectArray(businessComponent.getComponentsSetting(), String.class);
		List<BusinessComponentsSetting> dashboardComponentsList = businessComponentsSettingMapper
				.selectBatchIds(componentIds);

		// 4.1、如果组件ID列表和组件列表的大小不一致，抛出异常
		if (componentIds.size() != dashboardComponentsList.size()) {
			throw new BusinessException(SituationErrorCode.TEMPLATE_DATA_ERROR);
		}

		// 4.2、构造组件列表数据
		for (BusinessComponentsSetting dashboardComponents : dashboardComponentsList) {
			String resourceId = IDGeneratorUtil.getID();
			// 替换组件ID列表中的原有组件ID
			componentIds.set(componentIds.indexOf(dashboardComponents.getResourceId()), resourceId);
			dashboardComponents.setResourceId(resourceId);
		}

		// 5、重写组件ID列表
		businessComponent.setComponentsSetting(JsonUtil.toJsonString(componentIds));

		// 6、导出业务组件数据
		return exportTemplate(tempDir, businessComponent, dashboardComponentsList);

	}

	/**
	 * 导入业务组件
	 *
	 * @param dataJson     json数据
	 * @param absolutePath 绝对路径
	 */
	@Override
	public void importBusinessComponent(String dataJson, String absolutePath) {
		// 1.写入业务组件数据
		ExportBusinessComponentContext exportTemplateContext = JsonUtil.toJsonObject(
				dataJson.replace("OUTER_HOST", SystemPropUtil.getProp("OUTER_HOST")),
				ExportBusinessComponentContext.class);

		// 校验字段长度是否合法
		// situationHandlerService.checkFieldLen(exportTemplateContext.getBusinessComponent().getName(),
		// null);

		// 2.列出要导入的所有可能的文件路径
		List<String> filePaths = getComponentsFilePaths(
				exportTemplateContext.getBusinessComponentsSettingList());
		filePaths.add(exportTemplateContext.getBusinessComponent().getThumbnail());

		// 3.列出要导入的实际的文件路径（都在同一级目录）
		List<String> files = FileUtil.listFileNamesByDir(absolutePath, false);

		// 4.获取要导入的文件路径对应服务器应该存放的路径map
		Map<String, String> filePathMap = StreamApiUtils.toMap(files, file -> file, file -> {
			for (String filePath : filePaths) {
				if (filePath != null && filePath.contains(file)) {
					return filePath;
				}
			}
			return file;
		});

		// 5.移动文件到对应服务器应该存放的路径
		for (String file : files) {
			String targetPath = filePathMap.get(file);
			if (targetPath.startsWith(ConfigUtil.getContextProperty("cr.local.file.prefix"))) {
				FileUtil.copyFile(absolutePath + File.separator + file, targetPath, false);
			}

		}

		// 6.导入实际数据
		insertTemplate(exportTemplateContext.getBusinessComponent(),
				exportTemplateContext.getBusinessComponentsSettingList());

	}

	@Override
	public BusinessComponent selectBusinessComponentById(String businessComponentId) {
		return businessComponentMapper.selectById(businessComponentId);
	}

	/**
	 * 获得组件文件路径
	 *
	 * @param dashboardComponentsList 仪表板组件列表
	 * @return {@link List}<{@link String}>
	 */
	private List<String>
			getComponentsFilePaths(List<BusinessComponentsSetting> dashboardComponentsList) {
		List<String> files = Lists.newArrayList();
		for (BusinessComponentsSetting dashboardComponent : dashboardComponentsList) {
			String componentSetting = dashboardComponent.getComponentSetting();
			JSONObject jsonObject = JSONObject.parseObject(componentSetting);
			files.add(jsonObject.getString("img"));
			files.add(jsonObject.getString("url"));
			files.add(jsonObject.getString("decoration_url"));
			files.add(jsonObject.getString("drawing_url"));
			files.add(jsonObject.getString("thumbnail"));
		}

		return StreamApiUtils.distinct(files);
	}

	/**
	 * 导出组件
	 *
	 * @param tempDir                 临时dir
	 * @param businessComponent       业务组件
	 * @param dashboardComponentsList 仪表板组件列表
	 * @return {@link String}
	 * @throws IOException ioexception
	 */
	private String exportTemplate(String tempDir, BusinessComponent businessComponent,
			List<BusinessComponentsSetting> dashboardComponentsList) throws IOException {

		// 1. 创建文件夹
		FileUtil.mkdirs(tempDir);

		// 2. 获取业务组件数据
		ExportBusinessComponentContext exportTemplateContext = new ExportBusinessComponentContext();

		exportTemplateContext.setBusinessComponent(businessComponent);
		exportTemplateContext.setBusinessComponentsSettingList(dashboardComponentsList);

		// 3. 获取业务组件中各种文件路径
		List<String> filePaths = getComponentsFilePaths(dashboardComponentsList);
		filePaths.add(businessComponent.getThumbnail());

		// 4. 拷贝文件
		for (String direction : filePaths) {
			if (direction == null) {
				continue;
			}

			File file = new File(direction);

			// 过滤不必要的文件夹 隐藏文件夹
			if (!file.isFile() || file.getName().startsWith(".")) {
				continue;
			}

			// 拷贝对应文件
			if (file.isFile()) {
				String fileName = file.getAbsolutePath();
				String newFile = fileName.replace(file.getParent(), "");
				FileUtil.copyFile(fileName, tempDir + newFile, false);
			}
		}

		String data = JsonUtil.toJsonString(exportTemplateContext)
				.replace(SystemPropUtil.getProp("OUTER_HOST"), "OUTER_HOST");
		// 5. 生成 data.json
		String jsonPath = tempDir + File.separator + CrCommonConstants.RESOURCE_FILE_NAME;
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(jsonPath),
				StandardCharsets.UTF_8);
		FileUtil.writeString(data, osw);
		// 5. 压缩 zip 文件, 返回路径
		return ZipUtil.toZip(tempDir);
	}

	/**
	 * 更新业务组件
	 *
	 * @param businessComponentReq 业务组件请求
	 * @return {@link String}
	 */
	@Override
	public String updateBusinessComponent(BusinessComponentReq businessComponentReq) {
		// 校验业务组件名称
		checkTemplateName(businessComponentReq.getName(), businessComponentReq.getResourceId());

		String resourceId = businessComponentReq.getResourceId();
		BusinessComponent BusinessComponent = selectBusinessComponentById(resourceId, true);

		JSONArray newComponentsList = new JSONArray();
		// 获取旧组件id
		List<String> oldComponentsList = JsonUtil
				.toJsonObjectArray(BusinessComponent.getComponentsSetting(), String.class);

		List<BusinessComponentsSettingReq> componentsSetting = businessComponentReq
				.getBusinessComponentsSettings();
		List<LayerShowReq> layerShow = businessComponentReq.getLayerShow();
		if (CollectionUtils.isNotEmpty(componentsSetting)
				&& CollectionUtils.isNotEmpty(layerShow)) {
			List<BusinessComponentsSettingReq> sortComponentsSettingList = sortComponentByLayer(
					componentsSetting, layerShow);
			for (BusinessComponentsSettingReq dashboardComponentsReq : sortComponentsSettingList) {
				String newResourceId = IDGeneratorUtil.getID();
				newComponentsList.add(newResourceId);
				dashboardComponentsReq.setResourceId(newResourceId);
			}
		}

		BusinessComponent toBusinessComponent = convertComponent(businessComponentReq);
		toBusinessComponent.setComponentsSetting(newComponentsList.toString());
		List<BusinessComponentsSetting> dashboardComponentsList = changeToBusinessComponentsSetting(
				componentsSetting);

		// 删除旧组件信息，插入新组件
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				businessComponentMapper.updateById(toBusinessComponent);
				if (CollectionUtils.isNotEmpty(oldComponentsList)) {
					businessComponentsSettingMapper.deleteBatchIds(oldComponentsList);
				}
				if (CollectionUtils.isNotEmpty(dashboardComponentsList)) {
					businessComponentsSettingMapper.batchInsert(dashboardComponentsList);
				}
			}
		});
		return resourceId;
	}

	/**
	 * 得到业务组件信息
	 *
	 * @param resourceId 资源id
	 * @param type       类型
	 * @param isPreview  是预览
	 * @return {@link BusinessComponentRes}
	 */
	@Override
	public BusinessComponentRes getBusinessComponentInfo(String resourceId, String type,
			Boolean isPreview) {
		// 1、获取业务组件数据
		BusinessComponent businessComponent = selectBusinessComponentById(resourceId, true);

		// 2、构造返回业务组件数据
		BusinessComponentRes businessComponentRes = BeanUtils.copy(businessComponent,
				BusinessComponentRes.class);
		// 2-1 处理组件配置数据
		businessComponentRes.setBusinessComponentsSettings(
				convertComponents(businessComponent.getComponentsSetting(), type));

		return businessComponentRes;
	}

	/**
	 * 删除业务组件
	 *
	 * @param idList id列表
	 * @return {@link Integer}
	 */
	@Override
	public Integer deleteBusinessComponent(List<String> idList) {
		if (CollectionUtils.isNotEmpty(idList)) {
			List<BusinessComponent> businessComponents = businessComponentMapper
					.selectBatchIds(idList);
			List<String> dashboardComponentsIdList = new ArrayList<>();
			businessComponents.forEach(businessComponent -> dashboardComponentsIdList
					.addAll(JsonUtil.toJsonObjectArray(businessComponent.getComponentsSetting(),
							String.class)));
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					businessComponentMapper.deleteBatchIds(idList);
					if (CollectionUtils.isNotEmpty(dashboardComponentsIdList)) {
						businessComponentsSettingMapper.deleteBatchIds(dashboardComponentsIdList);
					}
				}
			});
		}
		return idList.size();
	}

	/**
	 * 获得业务组件页面
	 *
	 * @param businessComponentPageReq 业务组件页面请求
	 * @return {@link ListRsp}<{@link BusinessComponentRes}>
	 */
	@Override
	public ListRsp<BusinessComponentRes>
			getBusinessComponentPage(BusinessComponentPageReq businessComponentPageReq) {
		Page page = businessComponentPageReq.toPageParam();

		IPage<BusinessComponent> businessComponentIPage = businessComponentMapper.selectPage(page,
				businessComponentPageReq);
		IPage<BusinessComponentRes> businessComponentResIPage = businessComponentIPage
				.convert((businessComponent -> {
					BusinessComponentRes businessComponentRes = BeanUtils.copy(businessComponent,
							BusinessComponentRes.class);
					businessComponentRes.setBusinessComponentsSettings(
							convertComponents(businessComponent.getComponentsSetting(), null));
					return businessComponentRes;
				}));

		return PageUtil.getListResult(businessComponentResIPage);
	}

	/**
	 * 获得组件列表
	 *
	 * @param resourceId 资源id
	 * @return {@link List}<{@link String}>
	 */
	@Override
	public List<String> getComponentsList(String resourceId) {
		BusinessComponent businessComponent = businessComponentMapper.selectById(resourceId);
		if (businessComponent == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		String componentsSetting = businessComponent.getComponentsSetting();
		List<String> componentsIdList = new ArrayList<>();
		if (StringUtils.isNotBlank(componentsSetting)) {
			componentsIdList.addAll(JsonUtil.toJsonObjectArray(componentsSetting, String.class));
		}
		return componentsIdList;
	}

	/**
	 * 转换组件
	 * 转换业务组件配置为业务组件实体
	 *
	 * @param businessComponentReq 仪表板业务组件要求
	 * @return {@link BusinessComponent}
	 */
	private BusinessComponent convertComponent(BusinessComponentReq businessComponentReq) {

		// 1、复制基本信息
		BusinessComponent businessComponent = BeanUtils.copy(businessComponentReq,
				BusinessComponent.class);

		// 2、处理组件配置数据
		JSONArray jsonArray = new JSONArray();
		List<BusinessComponentsSettingReq> componentsSetting = businessComponentReq
				.getBusinessComponentsSettings();

		List<LayerShowReq> layerShow = businessComponentReq.getLayerShow();

		if (CollectionUtils.isNotEmpty(componentsSetting)) {
			jsonArray.addAll(componentsSetting);
		}

		businessComponent.setComponentsSetting(jsonArray.toString());

		if (CollectionUtils.isNotEmpty(layerShow)) {
			JSONArray layerShowArray = new JSONArray();
			layerShowArray.addAll(layerShow);
			businessComponent.setLayerShow(layerShowArray.toString());
		}

		return businessComponent;
	}

	/**
	 * 改变业务组件设置
	 *
	 * @param dashboardComponentsReqList 指示板组件请求列表
	 * @return {@link List}<{@link BusinessComponentsSetting}>
	 */
	private List<BusinessComponentsSetting> changeToBusinessComponentsSetting(
			List<BusinessComponentsSettingReq> dashboardComponentsReqList) {
		List<BusinessComponentsSetting> dashboardComponentsList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(dashboardComponentsReqList)) {
			for (BusinessComponentsSettingReq dashboardComponentsReq : dashboardComponentsReqList) {
				BusinessComponentsSetting dashboardComponents = BeanUtils
						.deepCopy(dashboardComponentsReq, BusinessComponentsSetting.class);
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
				if (measure.startsWith("[") && measure.endsWith("]")) {
					measureList.addAll(JsonUtil.toJsonObjectArray(measure, String.class));
				} else {
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

	/**
	 * 解析api数据
	 *
	 * @param jsonObject        json对象
	 * @param componentsDataReq 组件数据请求
	 * @return {@link JSONArray}
	 */
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
		apiConnectReq.setResourceId(jsonObject.getString("chartApi"));

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
	 * 校验业务组件名称是否存在
	 *
	 * @param name       名称
	 * @param resourceId 业务组件ID
	 */
	private void checkTemplateName(String name, String resourceId) {

		// 1、通过名称查询已存在业务组件ID
		String oldResourceId = businessComponentMapper.selectByName(name);

		// 2、如果已存在业务组件ID为空，或者已存在业务组件ID不为空，但是与当前业务组件ID相等，返回null
		if (StringUtils.isBlank(oldResourceId) || Objects.equals(oldResourceId, resourceId)) {
			return;
		}

		// 3、否则，抛出名称重复异常
		throw new BusinessException(ErrorCode.NAME_DUPLICATE);
	}

	/**
	 * 根据层组件进行排序
	 *
	 * @param componentsSettingList 组件设置列表
	 * @param layerShow             层显示
	 * @return {@link List}<{@link BusinessComponentsSettingReq}>
	 */
	@Override
	public List<BusinessComponentsSettingReq> sortComponentByLayer(
			List<BusinessComponentsSettingReq> componentsSettingList,
			List<LayerShowReq> layerShow) {

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

	/**
	 * 解析参数
	 *
	 * @param instanceDataQueryReq 实例数据查询请求
	 * @param apiRequestParams     api请求参数
	 */
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

	/**
	 * 解析数据库参数
	 *
	 * @param instanceDataQueryReq 实例数据查询请求
	 * @param jsonArray            json数组
	 */
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

}
