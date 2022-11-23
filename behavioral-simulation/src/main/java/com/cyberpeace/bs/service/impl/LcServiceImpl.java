package com.cyberpeace.bs.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.bs.dao.mapper.DemoMapper;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.TargetBo;
import com.cyberpeace.bs.service.bo.behavior.*;
import com.cyberpeace.bs.service.bo.demo.GetDeviceReq;
import com.cyberpeace.bs.service.bo.demo.LcDevicePageRes;
import com.cyberpeace.bs.service.bo.demo.ReleaseTaskReqDemo;
import com.cyberpeace.bs.service.bo.lc.LcVariableBase;
import com.cyberpeace.bs.service.bo.lc.LcVariableModifyReq;
import com.cyberpeace.bs.service.bo.lc.LcVariableRspBo;
import com.cyberpeace.bs.service.bo.lc.ReleaseTaskReq;
import com.cyberpeace.bs.service.bo.model.Behavior;
import com.cyberpeace.bs.service.bo.variable.VariableBo;
import com.cyberpeace.bs.service.bo.variable.VariableListBo;
import com.cyberpeace.bs.service.bo.variable.VariableModifyReq;
import com.cyberpeace.bs.service.bo.variable.VariableSimple;
import com.cyberpeace.bs.utils.LcHttpClient;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LcServiceImpl implements LcService {

	@Autowired
	DemoMapper demoMapper;

	private String TOKEN;

	private String LC_HOST_PORT;

	private String BEHAVIOR_LIST_URL;

	private String ACTUAL_DEVICE_URL;

	private String START_BEHAVIOR_URL;

	private String TARGET_LIST_URL;
	/**
	 * 龙测请求对接地址
	 */
	private String VARIABLE_URL = "api/v1/data/find-by-page";
	private String VARIABLE_UPDATE_URL = "api/v1/data/update";

	private String RELEASE_TASK = "api/v1/save";

	private String DEVICE_URL = "api/v1/devices/find-by-page";

	private String BEHAVIOR_PAGE = "api/v1/newList";

	private String BEHAVIOR_VARIABLE = "api/v1/flowData/";

	private String BEHAVIOR_VARIABLE_UPDATE = "api/v1/updateFlowData";

	@PostConstruct
	public void init() {
		// LC_HOST_PORT = SystemPropUtil.getProp("LC_HOST_PORT");
		LC_HOST_PORT = "http://10.4.1.138/";
		// BEHAVIOR_LIST_URL = LC_HOST_PORT +
		// ConfigUtil.getContextProperty("lc.behavior.list");
		BEHAVIOR_LIST_URL = "api/v1/flowchart/find-by-page";
		// TARGET_LIST_URL = LC_HOST_PORT +
		// ConfigUtil.getContextProperty("lc.target.list");
	}

	@Override
	public ListRsp<LcBehavior> getLcBehaviorList(BehaviorPageReq pageReq) {
		String targetUrl = LC_HOST_PORT + BEHAVIOR_PAGE;
		LcBehaviorListReqBody body = new LcBehaviorListReqBody();
		body.setPage(pageReq.getPage());
		body.setSize(pageReq.getPerPage());
		body.setFlowChartName(pageReq.getPattern());
		body.setNetModel(pageReq.getNetworkType());
		LcBehaviorPageRes lcBehaviorPageRes = LcHttpClient.post(targetUrl, body,
				LcBehaviorPageRes.class);
		ListRsp<LcBehavior> res = new ListRsp<>();
		List<LcBehavior> list = Lists.newArrayList();
		for (LcBehaviorRspBo record : lcBehaviorPageRes.getRecords()) {
			LcBehavior lcBehavior = createLcBehavior(record);
			list.add(lcBehavior);
		}
		res.setList(list);
		res.setTotal(lcBehaviorPageRes.getTotal());
		res.setCurrentPage(pageReq.getPage());
		res.setLastPage(res.getTotal() / pageReq.getPerPage() + 1);
		return res;
	}

	@Override
	public void startBehavior(ReleaseTaskReq req) {
		String targetUrl = LC_HOST_PORT + RELEASE_TASK;
		LcHttpClient.post(targetUrl, req, String.class);
	}

	@Override
	public void stopBehavior(String targetIp, String behaviorId) {
		// todo
	}

	private LcBehavior createLcBehavior(LcBehaviorRspBo rspBo) {
		LcBehavior lcBehavior = new LcBehavior();
		lcBehavior.setId(rspBo.getId());
		lcBehavior.setName(rspBo.getFlowChartName());
		lcBehavior.setAccessOs(rspBo.getDeviceType());
		lcBehavior.setNetworkType(rspBo.getNetModel());
		lcBehavior.setStepCount(rspBo.getStepNum());
		lcBehavior.setTimeCost(rspBo.getConsumeTime());
		lcBehavior.setCreateTime(rspBo.getCreateTime());
		lcBehavior.setCreatorName(rspBo.getCreatorName());
		lcBehavior.setApplication(rspBo.getApplication());
		List<String> tags = Lists.newArrayList();
		tags.add("上网");
		lcBehavior.setTag(tags);
		return lcBehavior;
	}



	@Override
	public List<TargetBo> getDeviceList(String search) {
		String targetUrl = TARGET_LIST_URL + "/?search=" + search;
		JSONArray res = HttpClientUtil.rawGet(targetUrl, JSONArray.class);
		List<TargetBo> ret = Lists.newArrayList();
		// todo

		return ret;
	}

	@Override
	public List<Behavior> getLcBehaviorListByIds(Collection<String> behaviorIds,
			Boolean withVariable) {
		BehaviorPageReq pageReq = new BehaviorPageReq();
		pageReq.setPerPage(100);
		ListRsp<LcBehavior> listRsp = getLcBehaviorList(pageReq);
		List<LcBehavior> lcBehaviors = listRsp.getList();
		List<Behavior> res = Lists.newArrayList();
		lcBehaviors.forEach(lcBehavior -> {
			if (behaviorIds.contains(lcBehavior.getId())) {
				Behavior behavior = BeanUtils.copy(lcBehavior, Behavior.class);
				res.add(behavior);
			}
		});
		return res;
	}

	@Override
	public VariableListBo getVariableList(String behaviorId) {
		String targetUrl = LC_HOST_PORT + BEHAVIOR_VARIABLE + behaviorId;
		String res = LcHttpClient.rawGet(targetUrl, String.class);
		List<LcVariableRspBo> lcVariableRspBos = JsonUtil.toJsonObjectArray(res,
				LcVariableRspBo.class);
		VariableListBo listBo = new VariableListBo();
		listBo.setBehaviorId(behaviorId);
		List<VariableBo> variableBos = Lists.newArrayList();
		lcVariableRspBos.forEach(lcVariable -> {
			VariableBo variableBo = new VariableBo();
			variableBo.setId(lcVariable.getDataId());
			variableBo.setName(lcVariable.getName());
			variableBo.setValue(lcVariable.getVarValue());
			variableBo.setUpdateTime(lcVariable.getUpdateTime());
			variableBos.add(variableBo);
		});
		listBo.setVariables(variableBos);
		return listBo;
	}

	@Override
	public void updateVariable(VariableModifyReq req) {
		String targetUrl = LC_HOST_PORT + BEHAVIOR_VARIABLE_UPDATE;
		LcVariableModifyReq body = new LcVariableModifyReq();

		for (VariableSimple variable : req.getVariables()) {
			LcVariableBase lcVariable = new LcVariableBase();
			lcVariable.setDataId(variable.getId());
			lcVariable.setName(variable.getName());
			lcVariable.setVarValue(variable.getValue());
			body.getEntityList().add(lcVariable);
		}

		LcHttpClient.patch(targetUrl, body, String.class);
	}

	@Override
	public void startDemoBehavior(ReleaseTaskReqDemo req) {
		String targetUrl = LC_HOST_PORT + RELEASE_TASK;
		LcHttpClient.post(targetUrl, req, String.class);
	}

	@Override
	public String getDeviceId() {
		String targetUrl = LC_HOST_PORT + DEVICE_URL;
		GetDeviceReq body = new GetDeviceReq();
		LcDevicePageRes res = LcHttpClient.post(targetUrl, body, LcDevicePageRes.class);
		return res.getRecords().get(0).getId();
	}
}