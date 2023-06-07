package com.cyberpeace.integratation.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.cyberpeace.common.util.SystemPropUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.situation.BcContext;
import com.cyberpeace.integratation.model.situation.ModelContext;
import com.cyberpeace.integratation.model.situation.ModelTypeContext;
import com.cyberpeace.integratation.service.RemoteOperationService;
import com.google.common.collect.Maps;

@Service
public class RemoteOperationServiceImpl implements RemoteOperationService {

	private volatile Map<String, ModelContext> modelListCache = Maps.newHashMap();

	private volatile Map<String, ModelTypeContext> modelTypeListCache = Maps.newHashMap();

	private String YW_BC_LIST;

	private String YW_MODEL_LIST;

	private String YW_MODEL_TYPE_LIST;

	private String YW_TOKEN;

	@Autowired
	private SituationEnvService situationEnvService;

	@PostConstruct
	public void init() {
		String outerHostYw = situationEnvService.getYwHost();
		String ywCode = SystemPropUtil.getProp("YW_CODE") == null ? "A102": SystemPropUtil.getProp("YW_CODE");

		YW_BC_LIST = outerHostYw + ConfigUtil.getContextProperty("yw.bc.list").replace("YW_CODE",ywCode);
		YW_MODEL_LIST = outerHostYw + ConfigUtil.getContextProperty("yw.model.list").replace("YW_CODE",ywCode);
		YW_MODEL_TYPE_LIST = outerHostYw + ConfigUtil.getContextProperty("yw.model.type.list").replace("YW_CODE",ywCode);
		YW_TOKEN = ConfigUtil.getContextProperty("yw.token");
	}

	@Override
	public List<BcContext> getBcList(String type) {
		String rsp = HttpClientUtil.ywGet(YW_BC_LIST + type, String.class, "获取运维系统靶场数据失败");
		List<BcContext> res = JsonUtil.toJsonObjectArray(rsp, BcContext.class);
		return res;
	}

	@Override
	public List<ModelTypeContext> getModelTypeList() {
		String rsp = HttpClientUtil.ywGet(YW_MODEL_TYPE_LIST, String.class, "获取运维系统模型类型数据失败");
		List<ModelTypeContext> res = JsonUtil.toJsonObjectArray(rsp, ModelTypeContext.class);
		return res;
	}

	@Override
	public List<ModelContext> getModelList() {
		String rsp = HttpClientUtil.ywGet(YW_MODEL_LIST, String.class, "获取运维系统模型数据失败");
		List<ModelContext> res = JsonUtil.toJsonObjectArray(rsp, ModelContext.class);
		return res;
	}

	@Override
	public Map<String, ModelContext> getModelListMap() {
		if (modelListCache.isEmpty()) {
			modelListInit();
		}
		return modelListCache;
	}

	private void modelListInit() {
		List<ModelContext> modelList = getModelList();
		modelList.forEach(model -> {
			modelListCache.put(model.getId(), model);
		});
	}

	@Override
	public Map<String, ModelTypeContext> getModelTypeListMap() {
		if (modelTypeListCache.isEmpty()) {
			modelTypeListInit();
		}
		return modelTypeListCache;
	}

	private void modelTypeListInit() {
		List<ModelTypeContext> modelTypeList = getModelTypeList();
		modelTypeList.forEach(modelType -> {
			modelTypeListCache.put(modelType.getId(), modelType);
		});
	}
}
