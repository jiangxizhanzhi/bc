package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionTemplateStatus;
import com.cyberpeace.cr.crbase.constant.NodeOperate;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.dao.mapper.CrConstantsConfigMapper;
import com.cyberpeace.cr.evaluation.controller.req.EvalInstanceMissionTemplateReq;
import com.cyberpeace.cr.evaluation.controller.req.ManualExecutionReq;
import com.cyberpeace.cr.evaluation.controller.req.UpdateEvalMissionTemplateReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalActionProsecuteRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalMissionLogRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalMissionTemplateRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalMissionTemplateLogMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalMissionTemplateMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalNodeLogMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.service.EvalMissionService;
import com.cyberpeace.cr.evaluation.service.EvalMissionTemplateService;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.impl.bo.*;
import com.cyberpeace.integratation.service.RemoteUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "靶场测评-前台-任务")
@Slf4j
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/task")
public class EvalMissionController {

    @Autowired
    private EvalMissionTemplateService evalMissionTemplateService;

    @Autowired
    private EvalTaskMapper evalTaskMapper;

    @Autowired
    private EvalTemplateService evalTemplateService;

    @Autowired
    private EvalMissionTemplateMapper evalMissionTemplateMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private EvalMissionTemplateLogMapper evalMissionTemplateLogMapper;

    @Autowired
    private EvalNodeLogMapper evalNodeLogMapper;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private EvalMissionService evalMissionService;

    @Autowired
    private CrConstantsConfigMapper crConstantsConfigMapper;


    @ApiOperation("新增实例任务")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @PostMapping("/mission/instance")
    public BaseRsp<Boolean> createInstanceMission(@RequestBody @Validated EvalInstanceMissionTemplateReq evalMissionTemplateReq) {
        String taskId = evalMissionTemplateReq.getTaskId();
        EvalTask evalTask = evalTaskMapper.selectById(taskId);
        String resourceId = IDGeneratorUtil.getID();
        setTaskMission(evalMissionTemplateReq, evalTask, resourceId);

        EvalMissionTemplate evalMissionTemplate = BeanUtils.copy(evalMissionTemplateReq, EvalMissionTemplate.class);
        evalMissionTemplate.setTools(JsonUtil.toJsonString(evalMissionTemplateReq.getTools()));
        evalMissionTemplate.setResourceId(resourceId);
        EvalMissionTemplateLog evalMissionTemplateLog = new EvalMissionTemplateLog();
        evalMissionTemplateLog.setOperate(MissionTemplateStatus.NEW);
        evalMissionTemplateLog.setMissionId(resourceId);
        evalMissionTemplateLog.setMissionName(evalMissionTemplate.getName());
        UserInfo userContext = UserInfoContext.getUserContext();
        evalMissionTemplateLog.setCreateUserId(userContext.getUserId());
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                evalMissionTemplateMapper.insert(evalMissionTemplate);
                evalTaskMapper.updateById(evalTask);
                evalMissionTemplateLogMapper.insert(evalMissionTemplateLog);
            }
        });
        return new BaseRsp<>(true);
    }


    private void setTaskMission(EvalInstanceMissionTemplateReq evalMissionTemplateReq, EvalTask evalTask, String resourceId) {
        TechniqueWarfare missionScheme = JsonUtil.toJsonObject(evalTask.getTechniqueWarfare(), TechniqueWarfare.class);
        List<MissionData> stageList = missionScheme.getTools();
        Map<String, MissionData> stageDataMap = StreamApiUtils.toMap(stageList, MissionData::getStageName, s -> s);
        MissionData stageData = stageDataMap.get(evalMissionTemplateReq.getStageName());
        if (null == stageData) {
            throw new BusinessException(CrBusinessErrorCode.ERROR_STAGE_NAME);
        }
        List<String> taskList = stageData.getToolBoList();
        taskList.add(resourceId);
        stageList.set(stageList.indexOf(stageData), stageData);
        missionScheme.setTools(stageList);
        evalTask.setTechniqueWarfare(JsonUtil.toJsonString(missionScheme));
    }

    @ApiOperation("修改任务状态")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @PatchMapping("/mission/edit")
    public BaseRsp<EvalMissionTemplateRsp> updateMissionStatus(@RequestBody @Validated UpdateEvalMissionTemplateReq updateEvalMissionReq) {

        EvalMissionTemplate evalMissionTemplate = evalMissionTemplateMapper.selectById(updateEvalMissionReq.getResourceId());
        EvalMissionTemplateLog evalMissionTemplateLog = updateMissionLog(updateEvalMissionReq.getResourceId(), evalMissionTemplate.getName());
        Integer status = updateEvalMissionReq.getStatus();
        evalMissionTemplate.setStatus(status);
        evalMissionTemplate.setUpdateTime(new Date());
        // 状态修改 超时单独处理--
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                evalMissionTemplateMapper.updateById(evalMissionTemplate);
                evalMissionTemplateLogMapper.insert(evalMissionTemplateLog);
            }
        });

        return new BaseRsp<>(evalMissionTemplateService.getMissionTemplate(updateEvalMissionReq.getResourceId()));
    }

    private EvalMissionTemplateLog updateMissionLog(String resourceId, String name) {
        EvalMissionTemplateLog evalMissionTemplateLog = new EvalMissionTemplateLog();
        evalMissionTemplateLog.setOperate(MissionTemplateStatus.NEW);
        evalMissionTemplateLog.setMissionId(resourceId);
        UserInfo userContext = UserInfoContext.getUserContext();
        evalMissionTemplateLog.setCreateUserId(userContext.getUserId());
        evalMissionTemplateLog.setMissionName(name);
        return evalMissionTemplateLog;
    }

    @ApiOperation("异常数据-靶标异常")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @PatchMapping("/node/abnormal")
    public BaseRsp<List<EvalNodeLog>> getAbnormalNode(
            @ApiParam(name = "task_id", required = true, value = "任务id") @RequestParam(value = "task_id") String taskId,
            @ApiParam(name = "node_id", required = false, value = "节点id") @RequestParam(value = "node_id") String nodeId
    ) {
        List<EvalNodeLog> evalNodeLogs = evalNodeLogMapper.selectList(null);
        List<EvalNodeLog> nodeLogList = evalNodeLogs.stream().filter(s -> s.getTaskId().equals(taskId)).collect(Collectors.toList());
        if (!StringUtils.isEmpty(nodeId)) {
            nodeLogList = evalNodeLogs.stream().filter(s -> s.getNodeId().equals(nodeId)).collect(Collectors.toList());
        }
        nodeLogList.sort(Comparator.comparing(EvalNodeLog::getCreateTime).reversed());
        return new BaseRsp<>(nodeLogList);
    }

    @ApiOperation("异常数据-任务异常")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @PatchMapping("/mission/abnormal")
    public BaseRsp<List<EvalMissionTemplateLog>> getAbnormalMission(@ApiParam(name = "task_id", required = true, value = "任务id") @RequestParam(value = "task_id") String taskId) {
        List<EvalMissionTemplateLog> evalMissionTemplateLogs = evalMissionTemplateLogMapper.selectList(null);
        List<EvalMissionTemplateLog> templateLogList = evalMissionTemplateLogs.stream().filter(s -> s.getTaskId().equals(taskId)
                && (s.getOperate() == MissionTemplateStatus.FAIL || s.getOperate() == MissionTemplateStatus.OVERTIME)).
                sorted(Comparator.comparing(EvalMissionTemplateLog::getCreateTime).reversed()).collect(Collectors.toList());
        return new BaseRsp<>(templateLogList);
    }


    @ApiOperation("导调控制方案-任务完成进度")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @GetMapping("/mission/finish/")
    public BaseRsp<List<EvalMissionLogRsp>> getFinishedMissionByTime(@ApiParam(name = "resource_id", required = true, value = "资源id") @RequestParam(value = "resource_id") String resourceId) {
        List<EvalMissionLogRsp> allByTask = evalMissionTemplateLogMapper.getAllByTask(resourceId);
        return new BaseRsp<>(allByTask);
    }

    @ApiOperation("导调控制方案-操作内容")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @GetMapping("/mission/status")
    public BaseRsp<List<EvalMissionTemplateLog>> getMissionStatus(@ApiParam(name = "resource_id", required = true, value = "资源id") @RequestParam(value = "resource_id") String resourceId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("task_id", resourceId);
        Map<String, UserInfo> allUsers = remoteUserService.allUsers();
        List<EvalMissionTemplateLog> logList = evalMissionTemplateLogMapper.selectByMap(map);
        logList = logList.stream().map(s -> {
            String user = s.getCreateUserId();
            UserInfo userInfo = allUsers.get(user);
            s.setCreateUserId(userInfo.getName());
            return s;
        }).collect(Collectors.toList());
        logList.sort(Comparator.comparing(EvalMissionTemplateLog::getCreateTime).reversed());
        return new BaseRsp<>(logList);
    }

    @ApiOperation("行动监控")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @GetMapping("/action/mission")
    public BaseRsp<List<EvalActionProsecuteRsp>> prosecuteAction(@ApiParam(name = "resource_id", required = true, value = "资源id") @RequestParam(value = "resource_id") String resourceId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("task_id", resourceId);
        List<EvalMissionTemplateLog> templateLogs = evalMissionTemplateLogMapper.selectByMap(map);
        List<EvalNodeLog> evalNodeLogs = evalNodeLogMapper.selectByMap(map);
        List<CrConstantsConfig> all = crConstantsConfigMapper.getAll();
        Map<Integer, List<CrConstantsConfig>> constantGroup = StreamApiUtils.groupBy(all, CrConstantsConfig::getType);
        List<CrConstantsConfig> missionNames = constantGroup.get(MissionTemplateStatus.MISSION_CONSTANT);
        Map<Integer, String> missionMap = StreamApiUtils.toMap(missionNames, CrConstantsConfig::getCode, CrConstantsConfig::getCnName);
        List<CrConstantsConfig> nodeNames = constantGroup.get(NodeOperate.NODE_CONSTANT);
        Map<Integer, String> nodeMap = StreamApiUtils.toMap(nodeNames, CrConstantsConfig::getCode, CrConstantsConfig::getCnName);

        Map<String, UserInfo> userInfoMap = remoteUserService.allUsers();
        List<EvalActionProsecuteRsp> res = new ArrayList<>();

        templateLogs.forEach(evalMissionTemplateLog -> {
            EvalActionProsecuteRsp rsp = new EvalActionProsecuteRsp();
            rsp.setActionName(evalMissionTemplateLog.getMissionName());
            rsp.setOperateType(missionMap.get(evalMissionTemplateLog.getOperate()));
            UserInfo userInfo = userInfoMap.get(evalMissionTemplateLog.getCreateUserId());
            rsp.setName(userInfo.getName());
            res.add(rsp);
        });
        evalNodeLogs.forEach(evalNodeLog -> {
            EvalActionProsecuteRsp rsp = new EvalActionProsecuteRsp();
            rsp.setActionName(evalNodeLog.getNodeName());
            rsp.setCreateTime(evalNodeLog.getCreateTime());
            rsp.setOperateType(nodeMap.get(evalNodeLog.getOperate()));
            UserInfo userInfo = userInfoMap.get(evalNodeLog.getCreateUserId());
            rsp.setName(userInfo.getName());
            res.add(rsp);
        });
        res.sort(Comparator.comparing(EvalActionProsecuteRsp::getCreateTime).reversed());
        return new BaseRsp<>(res);
    }


    @ApiOperation("手动执行任务")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @PostMapping("/mission/manual")
    public BaseRsp<String> updateMissionManual(@RequestBody @Validated ManualExecutionReq manualExecutionReq) {
        String resourceId = manualExecutionReq.getMissionId();

        EvalMissionTemplate evalMissionTemplate = evalMissionTemplateMapper.selectById(resourceId);
        String taskId = evalMissionTemplate.getTaskId();
        EvalTask evalTask = evalTaskMapper.selectById(taskId);

        // 中台交互
        evalMissionService.executeCommand(manualExecutionReq, evalTask);
        String tools = evalMissionTemplate.getTools();
        List<ToolBo> toolBos = JsonUtil.toJsonObjectArray(tools, ToolBo.class);
        Map<String, ToolBo> toolBoMap = StreamApiUtils.toMap(toolBos, ToolBo::getToolName, s -> s);
        String tool = manualExecutionReq.getTool();
        ToolBo toolBo1 = toolBoMap.get(tool);
        if (Boolean.TRUE.equals(toolBo1.getIsExecute()) && toolBo1.getLimitTime() > 0) {
            return new BaseRsp<>("can't be executed repeatedly");
        }
        toolBo1.setIsExecute(true);
        toolBo1.setExecuteTime(new Date());
        toolBo1.setAction(manualExecutionReq.getCommand());
        toolBos.set(toolBos.indexOf(toolBo1), toolBo1);
        evalMissionTemplate.setTools(JsonUtil.toJsonString(toolBos));
        // 状态修改 超时单独处理--
        evalMissionTemplateMapper.updateById(evalMissionTemplate);
        String res = "success";
        return new BaseRsp<>(res);
    }

    @ApiOperation("查询任务信息列表")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @GetMapping("/missions")
    public BaseRsp<List<StageData>> getMissions(@ApiParam(name = "template_id", value = "测评靶场模板id", required = true) @RequestParam("template_id") String templateId,
                                                @ApiParam(name = "task_id", value = "测评任务id", required = false) @RequestParam(value = "task_id") String taskId) {
        EvalTemplate evalTemplate = evalTemplateService.getById(templateId);
        String missionsScheme = evalTemplate.getTechniqueWarfare();
        if (StringUtils.isEmpty(missionsScheme)) {
            return new BaseRsp<>(new ArrayList<>());
        }
        List<EvalMissionTemplate> evalMissionTemplates = evalMissionTemplateMapper.selectList(null);
        Map<String, EvalMissionTemplate> missionTemplateMap = StreamApiUtils.toMap(evalMissionTemplates, EvalMissionTemplate::getResourceId, s -> s);
        List<StageData> stageList = JsonUtil.toJsonObject(missionsScheme, MissionScheme.class).getStageList();
        if (null == stageList || stageList.isEmpty()) {
            return new BaseRsp<>(new ArrayList<>());
        }
        List<EvalMissionTemplate> tmp = new ArrayList<>();
        List<StageData> collect = stageList.stream().map(stageData -> {
            List<TaskBo> taskList = stageData.getTaskList();
            List<TaskBo> taskRes = new ArrayList<>();
            for (TaskBo task : taskList) {
                String resourceId = task.getResourceId();
                EvalMissionTemplate evalMissionTemplate = missionTemplateMap.get(resourceId);
                String tools = evalMissionTemplate.getTools();
                List<ToolBo> toolBos = JsonUtil.toJsonObjectArray(tools, ToolBo.class);
                toolBos.forEach(toolBo -> {
                    Boolean isExecute = toolBo.getIsExecute();
                    if(Boolean.TRUE.equals(isExecute)){
                        if(TimeUtil.addSeconds(toolBo.getExecuteTime(), 30).compareTo(new Date()) > 0){
                            toolBo.setLimitTime((int)(TimeUtil.addSeconds(toolBo.getExecuteTime(), 30).getTime() - System.currentTimeMillis())/1000);
                        } else{
                            toolBo.setLimitTime(0);
                            toolBo.setIsExecute(false);

                        }
                    }
                });
                String jsonString = JsonUtil.toJsonString(toolBos);
                evalMissionTemplate.setTools(jsonString);
                tmp.add(evalMissionTemplate);
                BeanUtils.copyProperties(evalMissionTemplate, task);
                Integer status = task.getStatus();
                if((status.equals(MissionTemplateStatus.FINISH) || status.equals(MissionTemplateStatus.FAIL))
                        && new Date(task.getCreateTime().getTime() + task.getLimitTime()).compareTo(new Date()) > 0){
                    task.setOvertimeStatus(MissionTemplateStatus.OVERTIME);
                }
                taskRes.add(task);
            }
            stageData.setTaskList(taskRes);
            return stageData;
        }).collect(Collectors.toList());
        evalMissionTemplateService.updateBatchById(tmp);
        return new BaseRsp<>(collect);
    }


}