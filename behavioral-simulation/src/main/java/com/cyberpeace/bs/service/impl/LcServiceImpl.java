package com.cyberpeace.bs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.TargetBo;
import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.bs.service.bo.behavior.BehaviorPageReq;
import com.cyberpeace.bs.service.bo.behavior.LcBehavior;
import com.cyberpeace.bs.service.bo.behavior.LcBehaviorPageRes;
import com.cyberpeace.bs.service.bo.demo.GetDeviceReq;
import com.cyberpeace.bs.service.bo.demo.LcDevicePageRes;
import com.cyberpeace.bs.service.bo.lc.*;
import com.cyberpeace.bs.service.bo.variable.VariableBo;
import com.cyberpeace.bs.service.bo.variable.VariableListBo;
import com.cyberpeace.bs.service.bo.variable.VariableModifyReq;
import com.cyberpeace.bs.service.bo.variable.VariableSimple;
import com.cyberpeace.bs.utils.BsUtil;
import com.cyberpeace.bs.utils.LcHttpClient;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LcServiceImpl implements LcService {

	private String LC_HOST_PORT;

	private String BEHAVIOR_LIST_URL = "api/v1/findFlowChart";

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
		body.setDeviceType(pageReq.getAccessOs());
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
		lcBehavior.setTimeCost(BsUtil.s2Min(rspBo.getConsumeTime()));
		lcBehavior.setCreateTime(rspBo.getCreateTime());
		lcBehavior.setCreatorName(rspBo.getCreatorName());
		lcBehavior.getApplication().add(rspBo.getApplication());
		lcBehavior.getTag().add(rspBo.getTag());
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
	public List<LcBehaviorRspBo> getLcBehaviorListByIds(Collection<String> behaviorIds,
			boolean withVariable) {
		String targetUrl = LC_HOST_PORT + BEHAVIOR_LIST_URL;
		LcBehaviorQuery query = new LcBehaviorQuery();
		query.addIds(behaviorIds);
		if (withVariable) {
			query.setWithVariable(true);
		}
		String rsp = LcHttpClient.post(targetUrl, query, String.class);
		List<LcBehaviorRspBo> behaviorRspBos = JsonUtil.toJsonObjectArray(rsp,
				LcBehaviorRspBo.class);

		return behaviorRspBos;
	}

	@Override
	public List<Behavior> getBehaviorListByIds(Collection<String> behaviorIds) {
		List<LcBehaviorRspBo> lcBehaviorRspBos = getLcBehaviorListByIds(behaviorIds, false);
		return lcBehaviorRspBos.stream().map(this::createBehavior).collect(Collectors.toList());
	}

	private Behavior createBehavior(LcBehaviorRspBo rspBo) {
		Behavior behavior = new Behavior();
		behavior.setAccessOs(rspBo.getDeviceType());
		behavior.setCreateTime(rspBo.getCreateTime());
		behavior.setCreatorName(rspBo.getCreatorName());
		behavior.setName(rspBo.getFlowChartName());
		behavior.setNetworkType(rspBo.getNetModel());
		behavior.setStepCount(rspBo.getStepNum());
		behavior.getTag().add(rspBo.getTag());
		behavior.setId(rspBo.getId());
		behavior.setTimeCost(BsUtil.s2Min(rspBo.getConsumeTime()));
		return behavior;
	}

	@Override
	public VariableListBo getVariableList(String behaviorId) {
		String targetUrl = LC_HOST_PORT + BEHAVIOR_VARIABLE + behaviorId;
		LcVariableRsp res = LcHttpClient.rawGet(targetUrl, LcVariableRsp.class);
		List<LcVariableRspBo> lcVariableRspBos = res.getVariables();
		VariableListBo listBo = new VariableListBo();
		listBo.setBehaviorId(behaviorId);
		if(CollectionUtils.isEmpty(lcVariableRspBos)){
			listBo.setVariables(Lists.newArrayList());
			return listBo;
		}
		List<VariableBo> variableBos = Lists.newArrayList();
		lcVariableRspBos.forEach(lcVariable -> {
			VariableBo variableBo = new VariableBo();
			variableBo.setId(lcVariable.getDataId());
			variableBo.setName(lcVariable.getName());
			variableBo.setNote("#todo");
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
		body.setFlowChartId(new Long(req.getBehaviorId()));

		for (VariableSimple variable : req.getVariables()) {
			LcVariableModifyBo lcVariable = new LcVariableModifyBo();
			lcVariable.setDataId(new Long(variable.getId()));
			lcVariable.setName(variable.getName());
			lcVariable.setVarValue(variable.getValue());
			lcVariable.setNote(variable.getNote());
			body.getVariables().add(lcVariable);
		}

		LcHttpClient.patch(targetUrl, body, String.class);
	}

	@Override
	public String getDeviceId() {
		String targetUrl = LC_HOST_PORT + DEVICE_URL;
		GetDeviceReq body = new GetDeviceReq();
		LcDevicePageRes res = LcHttpClient.post(targetUrl, body, LcDevicePageRes.class);
		return res.getRecords().get(0).getId();
	}

	@Override
	public List<LcActualDevice> getActualDevices(List<Integer> types) {
		String targetUrl = LC_HOST_PORT;
		LcActualDeviceReq body = new LcActualDeviceReq();
		body.setDeviceType(types);
		String res = LcHttpClient.post(targetUrl, body, String.class);
		List<LcActualDevice> devices = JsonUtil.toJsonObjectArray(res, LcActualDevice.class);
		return devices;
	}
}