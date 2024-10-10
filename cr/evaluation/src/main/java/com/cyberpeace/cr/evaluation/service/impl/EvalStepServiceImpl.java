package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.evaluation.controller.req.EvalStepLoadAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalStepLoadDeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.LoadSetReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTestStepListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalInstanceStepMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalStepMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalStepTemplateMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTestInstanceMapper;
import com.cyberpeace.cr.evaluation.service.EvalStepService;
import com.cyberpeace.cr.evaluation.service.EvalTestService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.SceneMpContext;
import com.cyberpeace.integratation.service.RemoteLoadService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.situation.service.TopoHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalStepServiceImpl extends ServiceImpl<EvalStepMapper, EvalStep> implements EvalStepService {

	@Autowired
	private EvalStepMapper evalStepMapper;

	@Autowired
	private EvalTestInstanceMapper evalTestInstanceMapper;

	@Autowired
	private EvalStepTemplateMapper evalStepTemplateMapper;

	@Autowired
	private EvalInstanceStepMapper evalInstanceStepMapper;

	@Autowired
	private RemoteLoadService remoteLoadService;

	@Autowired
	private LocalSceneService localSceneService;

	@Lazy
	@Autowired
	private EvalTestService evalTestService;


	@Autowired
	private TopoHandlerService topoHandlerService;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Override
	public Map<String, String> createByTemplate(List<String> templateIdList, String testId) {
		if (CollectionUtils.isEmpty(templateIdList)) {
			return new HashMap<>();
		}
		List<EvalStepTemplate> stepTemplateList = evalStepTemplateMapper.selectBatchIds(templateIdList);
		Map<String, EvalStepTemplate> stepTemplateMap = stepTemplateList.stream()
																		.collect(Collectors.toMap(EvalStepTemplate::getResourceId, evalStepTemplate -> evalStepTemplate));
		List<EvalStep> stepList = new ArrayList<>();
		Map<String, String> stepIdMap = new HashMap<>();
		for (int i = 0; i < templateIdList.size() ; i++) {
			String resourceId = templateIdList.get(i);
			EvalStepTemplate evalStepTemplate = stepTemplateMap.get(resourceId);
			EvalStep evalStep = new EvalStep();
			evalStep.setTestId(testId);
			evalStep.setName(evalStepTemplate.getName());
			evalStep.setProcess(evalStepTemplate.getProcess());
			evalStep.setSetting(evalStepTemplate.getSetting());
			evalStep.setStage(i);
			stepList.add(evalStep);
		}
		saveBatch(stepList);
		return stepIdMap;
	}

	@Override
	public List<EvalStep> selectByTestId(String testId) {
		return evalStepMapper.selectAllByTestId(testId);
	}

	@Override
	public List<EvalTestStepListRsp> getSteps(String testId) {
		List<EvalStep> stepList = evalStepMapper.selectAllByTestId(testId);
		List<EvalTestStepListRsp> stepListRsps = new ArrayList<>();
		for (EvalStep evalStep : stepList) {
			EvalTestStepListRsp stepListRsp = new EvalTestStepListRsp();
			stepListRsp.setStepId(evalStep.getResourceId());
			stepListRsp.setName(evalStep.getName());
			stepListRsp.setProcess(evalStep.getProcess());
			stepListRsp.setStage(evalStep.getStage());

			Map<String, String> loadCmdMap = evalStep.getLoadList().stream().collect(Collectors.toMap(LoadSetReq::getLoadId, LoadSetReq::getCommandSet));
			List<String> loadIds = evalStep.getLoadList().stream().map(LoadSetReq::getLoadId).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(loadIds)) {
				JSONArray loadList = remoteLoadService.getLoadByIds(loadIds);
				for (Object o : loadList) {
					JSONObject load = (JSONObject) o;
					load.put("cmd", loadCmdMap.getOrDefault(load.getString("id"), load.getString("default_cmd")));
				}
				stepListRsp.setLoadList(loadList);
			} else {
				stepListRsp.setLoadList(new JSONArray());
			}
			stepListRsps.add(stepListRsp);
		}
		return stepListRsps;
	}

	@Override
	public List<EvalTestStepListRsp> getInstanceSteps(String instanceId) {
		EvalTestInstance testInstance = evalTestInstanceMapper.selectById(instanceId);
		List<EvalTestStepListRsp> evalTestStepListRspList = new ArrayList<>();
		List<EvalInstanceStep> instanceStepList = evalInstanceStepMapper.selectByInstanceId(instanceId);
		List<String> stepIds = instanceStepList.stream().map(EvalInstanceStep::getStepId).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(stepIds)) {
			return new ArrayList<>();
		}
		List<EvalStep> stepList = evalStepMapper.selectBatchIds(stepIds);
		String sceneId = testInstance.getMpSceneId();
		JSONObject plugins = remoteSceneService.getById(sceneId, null).getJSONObject("scene").getJSONObject("plugins");
		JSONObject extendElementInfo = (JSONObject) ((JSONObject) plugins.getOrDefault("cpcloud",
				new JSONObject())).getOrDefault("elements", new JSONObject());
		Map<String, String> loadTaskMap = new HashMap<>();
		for (String elementId : extendElementInfo.keySet()) {
			JSONObject elementInfo = (JSONObject) extendElementInfo.get(elementId);
			String loadToolId = elementInfo.getString("load_tool_id");
			if (loadToolId != null) {
				loadTaskMap.put(loadToolId, elementInfo.getString("resource_id"));
			}
		}
		Map<String, EvalStep> stepMap = stepList.stream().collect(Collectors.toMap(EvalStep::getResourceId, e -> e));
		for (int i = 0; i < instanceStepList.size(); i++ ) {
			EvalInstanceStep instanceStep = instanceStepList.get(i);
			EvalStep step = stepMap.get(instanceStep.getStepId());
			EvalTestStepListRsp evalTestStepListRsp = BeanUtils.copy(step, EvalTestStepListRsp.class);
			evalTestStepListRsp.setStepId(instanceStep.getResourceId());
			if (instanceStep.getStartTime() != null) {
				evalTestStepListRsp.setStartTime(TimeUtil.format1(instanceStep.getStartTime()));
			}
			if (instanceStep.getEndTime() != null) {
				evalTestStepListRsp.setEndTime(TimeUtil.format1(instanceStep.getEndTime()));
			}
			evalTestStepListRsp.setStatus();
			if (testInstance.getInstanceName() != null) {
				evalTestStepListRsp.setPreStepStatus(EvalTestStepListRsp.Status.INIT.getCode());
				evalTestStepListRsp.mergeStatus();
			} else if (i == 0) {
				evalTestStepListRsp.setPreStepStatus(EvalTestStepListRsp.Status.FINISHED.getCode());
			} else {
				evalTestStepListRsp.setPreStepStatus(evalTestStepListRspList.get(i - 1).getStatus());
			}

			List<String> loadIds = step.getLoadList().stream().map(LoadSetReq::getLoadId).collect(Collectors.toList());
			Map<String, String> loadCmdMap = step.getLoadList().stream().collect(Collectors.toMap(LoadSetReq::getLoadId, LoadSetReq::getCommandSet));
			JSONArray loadList = remoteLoadService.getLoadByIds(loadIds);
			for (Object o : loadList) {
				JSONObject load = (JSONObject) o;
				load.put("cmd", loadCmdMap.getOrDefault(load.getString("id"), load.getString("default_cmd")));
				load.put("task_id", loadTaskMap.getOrDefault(load.getString("id"), ""));
			}
			evalTestStepListRsp.setLoadList(loadList);
			evalTestStepListRspList.add(evalTestStepListRsp);
		}
		Collections.sort(evalTestStepListRspList, Comparator.comparing(EvalTestStepListRsp::getStage));
		return evalTestStepListRspList;
	}

	@Override
	public Boolean startStep(String stepId) {
		EvalInstanceStep evalInstanceStep = evalInstanceStepMapper.selectById(stepId);
		evalInstanceStep.setStartTime(new Date());
		evalInstanceStepMapper.updateById(evalInstanceStep);
		return true;
	}

	@Override
	public Boolean finishStep(String stepId) {
		EvalInstanceStep evalInstanceStep = evalInstanceStepMapper.selectById(stepId);
		evalInstanceStep.setEndTime(new Date());
		evalInstanceStepMapper.updateById(evalInstanceStep);
		return true;
	}

	@Override
	public void deleteLoad(EvalStepLoadDeleteReq deleteReq) {
		String stepId = deleteReq.getStepId();
		EvalInstanceStep evalInstanceStep = evalInstanceStepMapper.selectById(stepId);
		if (evalInstanceStep != null) {
			stepId = evalInstanceStep.getStepId();
		}
		EvalStep evalStep = evalStepMapper.selectById(stepId);
		List<LoadSetReq> loadList = evalStep.getLoadList();
		loadList = loadList.stream().filter(load -> !load.getLoadId().equals(deleteReq.getLoadId())).collect(Collectors.toList());
		evalStep.setSettingList(loadList);
		evalStepMapper.updateById(evalStep);
	}

	@Override
	public void addLoad(EvalStepLoadAddReq addReq) {
		EvalStep evalStep = evalStepMapper.selectById(addReq.getStepId());
		if (evalStep == null) {
			EvalInstanceStep instanceStep = evalInstanceStepMapper.selectById(addReq.getStepId());
			evalStep = evalStepMapper.selectById(instanceStep.getStepId());
		}
		List<LoadSetReq> loadList =  evalStep.getLoadList();
		List<String> loadIds = loadList.stream().map(LoadSetReq::getLoadId).collect(Collectors.toList());
		if (loadIds.contains(addReq.getLoadId())) {
			throw new BusinessException(CrBusinessErrorCode.DUPLICATED_LOAD);
		}
		LoadSetReq loadSetReq = new LoadSetReq();
		loadSetReq.setLoadId(addReq.getLoadId());
		loadSetReq.setCommandSet(addReq.getCommand() != null ? addReq.getCommand() : "");
		loadList.add(loadSetReq);
		evalStep.setSettingList(loadList);

		JSONObject jsonObject = new JSONObject();
		JSONObject sceneConfigElement = new JSONObject();
		JSONObject position2 = new JSONObject();
		position2.put("x", addReq.getX());
		position2.put("y", addReq.getY());
		sceneConfigElement.put("position2", position2.toString());
		JSONObject pluginSceneConfigElement = new JSONObject();
		pluginSceneConfigElement.put("image_type", "load");
		pluginSceneConfigElement.put("load_task_name", addReq.getLoadName());
		pluginSceneConfigElement.put("load_task_describe", addReq.getLoadDescription());
		pluginSceneConfigElement.put("load_exec_command", addReq.getCommand());
		pluginSceneConfigElement.put("load_tool_id", addReq.getLoadId());
		jsonObject.put("scene_config_element", sceneConfigElement);
		jsonObject.put("plugin_scene_config_element", pluginSceneConfigElement);
		EvalTestInstance evalTestInstance = evalTestInstanceMapper.selectById(addReq.getTestInstanceId());
		jsonObject.put("scene_id", evalTestInstance.getMpSceneId());
		EvalTest evalTest = evalTestService.getEvalTest(evalStep.getTestId());
		jsonObject.put("cscene_id", evalTest.getTopoId());
		LocalScene localScene = localSceneService.getOrExceptionByMpSceneId(evalTestInstance.getMpSceneId());
		SceneMpContext context = new SceneMpContext();
		context.getPass_through().put(WebConstant.PASS_THROUGH_BUSINESS_ID,
				localScene.getBusinessInstanceId());
		context.setAccessUserId(localScene.getUserId());
		jsonObject.put("context", context);
		log.info("evalStepService.addLoad:{}", jsonObject.toJSONString());
		remoteLoadService.addLoad(jsonObject);

		evalStepMapper.updateById(evalStep);
	}


}
