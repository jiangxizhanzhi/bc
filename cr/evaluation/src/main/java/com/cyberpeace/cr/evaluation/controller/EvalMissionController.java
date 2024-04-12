package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.*;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.dao.mapper.CrConstantsConfigMapper;
import com.cyberpeace.cr.evaluation.controller.req.EvalInstanceMissionTemplateReq;
import com.cyberpeace.cr.evaluation.controller.req.ManualExecutionReq;
import com.cyberpeace.cr.evaluation.controller.req.UpdateEvalMissionTemplateReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
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
        evalMissionTemplate.setLimitTime(evalMissionTemplateReq.getLimitTime());
        evalMissionTemplate.setStatus(MissionTemplateStatus.NOT_START);
        evalMissionTemplate.setResourceId(resourceId);
        EvalMissionTemplateLog evalMissionTemplateLog = new EvalMissionTemplateLog();
        evalMissionTemplateLog.setOperate(MissionTemplateStatus.NEW);
        evalMissionTemplateLog.setMissionId(resourceId);
        evalMissionTemplateLog.setMissionName(evalMissionTemplate.getName());
        evalMissionTemplateLog.setTaskId(evalMissionTemplate.getTaskId());
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
        String techniqueWarfare = evalTask.getTechniqueWarfare();
        MissionScheme missionScheme = JsonUtil.toJsonObject(techniqueWarfare, MissionScheme.class);
        List<StageData> stageList = missionScheme.getStageList();
        String name = evalMissionTemplateReq.getName();
        stageList.forEach(stageData -> {
            if(stageData.getStageName().equals(evalMissionTemplateReq.getStageName())){
                List<TaskBo> taskList = stageData.getTaskList();
                List<String> column = StreamApiUtils.getColumn(taskList, TaskBo::getTaskName);
                if(column.contains(name)){
                    throw new BusinessException(ErrorCode.REPEATED_MISSION_NAME);
                }
                TaskBo taskBo = new TaskBo();
                taskBo.setResourceId(resourceId);
                taskBo.setStatus(0);
                taskBo.setTaskId(evalTask.getResourceId());
                taskBo.setTaskName(name);
                taskList.add(taskBo);
            }
        });
        missionScheme.setStageList(stageList);
        evalTask.setTechniqueWarfare(JsonUtil.toJsonString(missionScheme));
    }

    @ApiOperation("修改任务状态")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @PatchMapping("/mission/edit")
    public BaseRsp<EvalMissionTemplateRsp> updateMissionStatus(@RequestBody @Validated UpdateEvalMissionTemplateReq updateEvalMissionReq) {

        EvalMissionTemplate evalMissionTemplate = evalMissionTemplateMapper.selectById(updateEvalMissionReq.getResourceId());
        EvalMissionTemplateLog evalMissionTemplateLog = updateMissionLog(updateEvalMissionReq.getResourceId(), evalMissionTemplate.getName(), updateEvalMissionReq.getTaskId(), updateEvalMissionReq.getStatus());
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

    private EvalMissionTemplateLog updateMissionLog(String resourceId, String name, String id, Integer status) {
        EvalMissionTemplateLog evalMissionTemplateLog = new EvalMissionTemplateLog();
        evalMissionTemplateLog.setOperate(status);
        evalMissionTemplateLog.setMissionId(resourceId);
        evalMissionTemplateLog.setTaskId(id);
        UserInfo userContext = UserInfoContext.getUserContext();
        evalMissionTemplateLog.setCreateUserId(userContext.getUserId());
        evalMissionTemplateLog.setMissionName(name);
        return evalMissionTemplateLog;
    }

    @ApiOperation("异常数据-靶标异常")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @GetMapping("/node/abnormal")
    public BaseRsp<Page<EvalNodeLog>> getAbnormalNode(
            @ApiParam(name = "task_id", required = true, value = "任务id") @RequestParam(value = "task_id") String taskId,
            @ApiParam(name = "node_id", required = false, value = "节点id") @RequestParam(value = "node_id") String nodeId,
            @ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase
    ) {
        Page<EvalNodeLog> objectPage = new Page<>(pageReqBase.getPage(), pageReqBase.getPerPage());
        QueryWrapper<EvalNodeLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EvalNodeLog::getTaskId, taskId).orderByDesc(EvalNodeLog::getCreateTime);
        if (!StringUtils.isEmpty(nodeId)) {
            queryWrapper.lambda().eq(EvalNodeLog::getNodeId,nodeId);
        }
        Page<EvalNodeLog> res = evalNodeLogMapper.selectPage(objectPage,queryWrapper);
        List<EvalNodeLog> nodeLogList = res.getRecords();
        Map<String, UserInfo> userInfoMap = remoteUserService.allUsers();
        nodeLogList.forEach(evalNodeLog -> {
            UserInfo userInfo = userInfoMap.get(evalNodeLog.getCreateUserId());
            if(null == userInfo){
                return;
            }
            evalNodeLog.setCreateUserId(userInfo.getUserName());
        });
        res.setRecords(nodeLogList);
        return new BaseRsp<>(res);
    }

    @ApiOperation("异常数据-任务异常")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @GetMapping("/mission/abnormal")
    public BaseRsp<Page<EvalMissionTemplateLog>> getAbnormalMission(@ApiParam(name = "task_id", required = true, value = "任务id") @RequestParam(value = "task_id") String taskId,
                                                                    @ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase) {
        Page<EvalMissionTemplateLog> objectPage = new Page<>(pageReqBase.getPage(), pageReqBase.getPerPage());
        QueryWrapper<EvalMissionTemplateLog> queryWrapper = new QueryWrapper<>();
        List<Integer> operates = new ArrayList<>();
        operates.add(MissionTemplateStatus.FAIL);
        operates.add(MissionTemplateStatus.OVERTIME);
        queryWrapper.lambda().eq(EvalMissionTemplateLog::getTaskId, taskId).in(EvalMissionTemplateLog::getOperate, operates).orderByDesc(EvalMissionTemplateLog::getCreateTime);
        Page<EvalMissionTemplateLog> res = evalMissionTemplateLogMapper.selectPage(objectPage,queryWrapper);
        List<EvalMissionTemplateLog> evalMissionTemplateLogs = res.getRecords();
        Map<String, UserInfo> userInfoMap = remoteUserService.allUsers();
        evalMissionTemplateLogs.forEach(evalMissionTemplateLog -> {
            UserInfo userInfo = userInfoMap.get(evalMissionTemplateLog.getCreateUserId());
            if(null == userInfo){
                return;
            }
            evalMissionTemplateLog.setCreateUserId(userInfo.getName());
        });
        return new BaseRsp<>(res);
    }

    @ApiOperation("任务状态列表")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @GetMapping("/mission/status/list")
    public BaseRsp<List<EvalMissionOperateContentRsp>> getMissionStatusList() {
        List<EvalMissionOperateContentRsp> rsp = new ArrayList<>();
        List<Integer> listCode = EnumUtils.getListCode(MissionStatusTypeConvert.class);
        listCode = listCode.stream().filter(code-> code<MissionTemplateStatus.MISSION_CONSTANT).collect(Collectors.toList());
        listCode.forEach(code-> {
            EvalMissionOperateContentRsp contentRsp = new EvalMissionOperateContentRsp();
            String enumDescByCode = EnumUtils.getEnumDescByCode(code, MissionStatusTypeConvert.class);
            contentRsp.setOperate(enumDescByCode);
            contentRsp.setCode(code);
            rsp.add(contentRsp);
        });
        return new BaseRsp<>(rsp);
    }

    @ApiOperation("节点操作列表")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @GetMapping("/node/operate/list")
    public BaseRsp<List<EvalNodeOperateContentRsp>> getNodeLogList() {
        List<EvalNodeOperateContentRsp> rsp = new ArrayList<>();
        List<Integer> listCode = EnumUtils.getListCode(NodeLogTypeConvert.class);
        listCode = listCode.stream().filter(code-> code<NodeOperate.NODE_CONSTANT).collect(Collectors.toList());
        listCode.forEach(code-> {
            EvalNodeOperateContentRsp contentRsp = new EvalNodeOperateContentRsp();
            String enumDescByCode = EnumUtils.getEnumDescByCode(code, NodeLogTypeConvert.class);
            String content = Arrays.stream(NodeLogTypeConvert.class.getEnumConstants()).filter(t -> t.getCode().equals(code))
                    .map(NodeLogTypeConvert::getContent).findFirst().orElse(null);
            contentRsp.setOperate(enumDescByCode);
            contentRsp.setCode(code);
            contentRsp.setContent(content);
            rsp.add(contentRsp);
        });
        return new BaseRsp<>(rsp);
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
    public BaseRsp<Page<EvalMissionTemplateLog>> getMissionStatus(@ApiParam(name = "resource_id", required = true, value = "资源id") @RequestParam(value = "resource_id") String resourceId,
                                                                  @ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase) {
        Page<EvalMissionTemplateLog> objectPage = new Page<>(pageReqBase.getPage(), pageReqBase.getPerPage());
        Map<String, UserInfo> allUsers = remoteUserService.allUsers();
        QueryWrapper<EvalMissionTemplateLog> query = new QueryWrapper<>();
        query.lambda().eq(EvalMissionTemplateLog::getTaskId, resourceId).orderByDesc(EvalMissionTemplateLog::getCreateTime);
        Page<EvalMissionTemplateLog> res = evalMissionTemplateLogMapper.selectPage(objectPage, query);
        List<EvalMissionTemplateLog> logList = res.getRecords();
        logList = logList.stream().map(s -> {
            String user = s.getCreateUserId();
            UserInfo userInfo = allUsers.get(user);
            s.setCreateUserId(userInfo.getName());
            return s;
        }).collect(Collectors.toList());
        res.setRecords(logList);
        return new BaseRsp<>(res);
    }

    @ApiOperation("行动监控")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @GetMapping("/action/mission")
    public BaseRsp<ListRsp<EvalActionProsecuteRsp>> prosecuteAction(@ApiParam(name = "resource_id", required = true, value = "资源id") @RequestParam(value = "resource_id") String resourceId,
                                                                 @ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase) {
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
            rsp.setCreateTime(evalMissionTemplateLog.getCreateTime());
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
        ListRsp<EvalActionProsecuteRsp> evalActionProsecuteRspListRsp = PageUtil.pageList(res, pageReqBase.getPage(), pageReqBase.getPerPage());
        return new BaseRsp<>(evalActionProsecuteRspListRsp);
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
        String missionsScheme;
        if(StringUtils.isEmpty(taskId)){
            EvalTemplate evalTemplate = evalTemplateService.getById(templateId);
            missionsScheme = evalTemplate.getTechniqueWarfare();
        } else {
            EvalTask evalTask = evalTaskMapper.selectById(taskId);
            missionsScheme = evalTask.getTechniqueWarfare();
        }
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
            long count = taskList.stream().filter(taskBo -> taskBo.getStatus().equals(MissionTemplateStatus.FAIL) || taskBo.getStatus().equals(MissionTemplateStatus.FINISH)).count();
            if(count == taskList.size()){
                stageData.setStageStatus(MissionTemplateStatus.START);
            }
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