package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.req.EvalLoadResultAddReq;
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
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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

    @Autowired
    private SituationHandlerService situationHandlerService;

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
        for (int i = 0; i < templateIdList.size(); i++) {
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
            if (evalStep.getLoadList() != null && evalStep.getLoadList().size() > 0) {
                Map<String, String> loadCmdMap = evalStep.getLoadList()
                        .stream()
                        .filter(req -> req.getLoadId() != null)
                        .collect(Collectors.toMap(LoadSetReq::getLoadId, req -> req.getCommandSet() != null ? req.getCommandSet() : "defaultCommand"));
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
        Map<String, String> loadInstanceMap = new HashMap<>();
        for (String elementId : extendElementInfo.keySet()) {
            JSONObject elementInfo = (JSONObject) extendElementInfo.get(elementId);
            String loadToolId = elementInfo.getString("load_tool_id");
            if (loadToolId != null) {
                loadTaskMap.put(loadToolId, elementInfo.getString("resource_id"));
                loadInstanceMap.put(loadToolId, elementInfo.getString("load_resource_instance_id"));
            }
        }
        Map<String, EvalStep> stepMap = stepList.stream().collect(Collectors.toMap(EvalStep::getResourceId, e -> e));
        for (int i = 0; i < instanceStepList.size(); i++) {
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
                load.put("load_resource_instance_id", loadInstanceMap.getOrDefault(load.getString("id"), ""));
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
        List<LoadSetReq> loadList = evalStep.getLoadList();
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
        pluginSceneConfigElement.put("load_source", addReq.getLoadSource());
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

    @Override
    public void addLoadExecutionResult(EvalLoadResultAddReq addReq) {
//		String mpSceneId = localSceneService.getMpSceneIdByBusinessInstanceId(addReq.getSceneId());
        JSONObject scene = remoteSceneService.getById(addReq.getSceneId(), null);
        Map<String, JSONObject> elementsMap = scene.getJSONObject("scene")
                .getJSONObject("plugins")
                .getJSONObject("cpcloud")
                .getObject("elements", Map.class);
        List<JSONObject> elements = scene.getJSONObject("scene").getJSONObject("topology")
                .getObject("elements", List.class);
        Map<String, String> idNameMap = elements
                .stream()
                .collect(Collectors.toMap(e -> e.getString("id"), e -> e.getString("name")));
        Set<Map.Entry<String, JSONObject>> entries = elementsMap.entrySet();
        ArrayList<String> elementIds = new ArrayList<>();
        ArrayList<String> loadElementIds = new ArrayList<>();
        HashMap<String, String> taskIdToMap = new HashMap<>();
        for (Map.Entry<String, JSONObject> entry : entries) {
            String elementId = entry.getKey();
            JSONObject element = entry.getValue();

            if (StringUtils.isNotBlank(element.getString("load_tool_id"))) {
                taskIdToMap.put(element.getString("resource_id"), elementId);
                loadElementIds.add(elementId);
            } else {
                elementIds.add(elementId);
            }
        }
        Random random = new Random();
        JSONObject rbSituationAttack = new JSONObject();
        String srcNode = taskIdToMap.get(addReq.getTaskId());
        rbSituationAttack.put("code", "attack_event_element-element");
        rbSituationAttack.put("look_at_target", false);
        rbSituationAttack.put("scene_id", addReq.getSceneId());
        rbSituationAttack.put("scene_name", scene.getString("name"));
        rbSituationAttack.put("source_id", taskIdToMap.get(addReq.getTaskId()));
        rbSituationAttack.put("source_id_name", idNameMap.get(taskIdToMap.get(addReq.getTaskId())));
        if (elementIds.size() > 0) {
            rbSituationAttack.put("target_id", idNameMap.get(getdstNode(srcNode, elementIds, random)));
        } else {
            rbSituationAttack.put("target_id", idNameMap.get(getdstNode(srcNode, loadElementIds, random)));
        }
        rbSituationAttack.put("target_id_name", "testvmware1");
        rbSituationAttack.put("type", 0);
        rbSituationAttack.put("use_name", 1);
        JSONObject data = new JSONObject();
        data.put("category", "attack");
        data.put("description", "攻击阶段:漏洞扫描与验证");
        data.put("duration", 3);
        JSONObject lineStyle = new JSONObject();
        lineStyle.put("color", "#FF0000");
        lineStyle.put("line_type", "fly");
        lineStyle.put("width", 0);
        data.put("line_style", "solid");
        data.put("flow_type", lineStyle);
        rbSituationAttack.put("data", data);

        log.info("addLoadExecutionResult sendMessage begin.");
		/*String a = "[\n" +
				"    {\n" +
				"      \"name\": \"自定义动效\",\n" +
				"      \"status\": 1\n" +
				"    },\n" +
				"    {\n" +
				"      \"name\": \"1.攻击阶段:漏洞扫描与验证\",\n" +
				"      \"status\": 3\n" +
				"    }\n" +
				"  ]";
		String b = "[\n" +
				"    {\n" +
				"      \"name\": \"自定义动效\",\n" +
				"      \"status\": 0\n" +
				"    },\n" +
				"    {\n" +
				"      \"name\": \"\",\n" +
				"      \"status\": 2\n" +
				"    }\n" +
				"  ]";
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("code", "attack_schedule");
		jsonObject1.put("data", JSON.parseArray(a));

		situationHandlerService.pushDataToRoom(jsonObject1, addReq.getSceneId());*/
        situationHandlerService.pushDataToRoom(rbSituationAttack, addReq.getSceneId());
		/*jsonObject1.put("data", JSON.parseArray(b));
		situationHandlerService.pushDataToRoom(jsonObject1, addReq.getSceneId());
		System.out.println("66666");*/

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            String json = objectMapper.writeValueAsString(addReq);
            JSONObject addReqJson = JSON.parseObject(json);
            remoteSceneService.addLoadResult(addReqJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getdstNode(String srcNode, List<String> ids, Random random) {
        String dstNode = ids.get(random.nextInt(ids.size()));
        if (ids.size() >= 2 && srcNode.equals(dstNode)) {
            dstNode = getdstNode(srcNode, ids, random);
        }
        return dstNode;
    }

}
