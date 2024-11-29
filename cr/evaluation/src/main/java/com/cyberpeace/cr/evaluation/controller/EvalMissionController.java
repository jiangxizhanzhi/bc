package com.cyberpeace.cr.evaluation.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.*;
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
import com.cyberpeace.cr.evaluation.service.EvalTaskStepService;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;
import com.cyberpeace.cr.evaluation.service.impl.bo.MissionScheme;
import com.cyberpeace.cr.evaluation.service.impl.bo.StageData;
import com.cyberpeace.cr.evaluation.service.impl.bo.TaskBo;
import com.cyberpeace.cr.evaluation.service.impl.bo.ToolBo;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
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

    @Autowired
    private EvalTaskStepService evalTaskStepService;


    @ApiOperation("新增实例任务")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @PostMapping("/mission/instance")
    public BaseRsp<Boolean> createInstanceMission(@RequestBody @Validated EvalInstanceMissionTemplateReq evalMissionTemplateReq) {

        if (evalMissionTemplateReq.getLimitTime() == null || evalMissionTemplateReq.getLimitTime() <= 0) {
            throw new BusinessException(CrBusinessErrorCode.LIMITED_TIME_CANNOT_BE_ZERO_OR_LESS_THAN_ZERO);
        }

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

        // 当前步骤
        Integer stage = getStage(evalMissionTemplate, updateEvalMissionReq.getTaskId(), updateEvalMissionReq.getTemplateId());

        Integer templateStatus = evalMissionTemplate.getStatus();
        if(templateStatus.equals(MissionTemplateStatus.FAIL) || templateStatus.equals(MissionTemplateStatus.FINISH)){
            return new BaseRsp<>(evalMissionTemplateService.getMissionTemplate(updateEvalMissionReq.getResourceId()));
        }
        EvalMissionTemplateLog evalMissionTemplateLog = updateMissionLog(updateEvalMissionReq.getResourceId(), evalMissionTemplate.getName(), updateEvalMissionReq.getTaskId(), updateEvalMissionReq.getStatus());
        Integer status = updateEvalMissionReq.getStatus();

        // 判断当前状态，暂停状态不计时，中止状态计算是否超时
        if (status.equals(MissionTemplateStatus.START)) {
            evalMissionTemplate.setStatus(status);
            evalMissionTemplate.setUpdateTime(new Date());
            // 记录剩余时间
            evalMissionTemplate.setRemainingTime((evalMissionTemplate.getLimitTime() * 60 * 1000L));
        } else if(status.equals(MissionTemplateStatus.PAUSE)){

            // 更新状态
            evalMissionTemplate.setStatus(status);
            //计算所用时间
            // 执行开始的时间
            long time = evalMissionTemplate.getUpdateTime().getTime();
            evalMissionTemplate.setUpdateTime(new Date());
            // 经过时间
            long l = evalMissionTemplate.getUpdateTime().getTime() - time;
            // 剩余时间
            evalMissionTemplate.setRemainingTime(evalMissionTemplate.getRemainingTime() - l);

        } else if (status.equals(MissionTemplateStatus.STOP)) {
            // 当执行中止命令时，先判断当前任务状态
            // 执行时
            if (templateStatus.equals(MissionTemplateStatus.START) ||
                    templateStatus.equals(MissionTemplateStatus.CONTINUE)) {
                //计算所用时间
                // 执行或继续开始的时间
                long time = evalMissionTemplate.getUpdateTime().getTime();
                evalMissionTemplate.setUpdateTime(new Date());
                // 经过时间
                long l = evalMissionTemplate.getUpdateTime().getTime() - time;
                // 剩余时间
                evalMissionTemplate.setRemainingTime(evalMissionTemplate.getRemainingTime() - l);
                // 更新状态
                evalMissionTemplate.setStatus(evalMissionTemplate.getRemainingTime() >= 0 ? status : MissionTemplateStatus.OVERTIME);
            } else if (templateStatus.equals(MissionTemplateStatus.PAUSE) ) {
                evalMissionTemplate.setStatus(evalMissionTemplate.getRemainingTime() >= 0 ? status : MissionTemplateStatus.OVERTIME);
            }

        } else {

            // 状态为3,5,6
            evalMissionTemplate.setStatus(status);
            evalMissionTemplate.setUpdateTime(new Date());
        }

        evalMissionTemplate.setTaskId(updateEvalMissionReq.getTaskId());
        if(status.equals(MissionTemplateStatus.FINISH) || status.equals(MissionTemplateStatus.FAIL)){
            evalMissionTemplate.setEndTime(new Date());
        }
        // 状态修改 超时单独处理--
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                evalMissionTemplateMapper.updateById(evalMissionTemplate);
                evalMissionTemplateLogMapper.insert(evalMissionTemplateLog);

                if(status.equals(MissionTemplateStatus.FINISH) || status.equals(MissionTemplateStatus.FAIL)){
                    EvalTaskStep evalTaskStep = new EvalTaskStep();
                    evalTaskStep.setStage(stage);
                    evalTaskStep.setTaskId(evalMissionTemplate.getTaskId());
                    evalTaskStep.setEndTime(evalMissionTemplate.getEndTime());
                    evalTaskStep.setTitle(evalMissionTemplate.getName());
                    evalTaskStepService.save(evalTaskStep);
                }
            }
        });

        return new BaseRsp<>(evalMissionTemplateService.getMissionTemplate(updateEvalMissionReq.getResourceId()));
    }

    private Integer getStage(EvalMissionTemplate evalMissionTemplate, String taskId, String templateId) {
        // 获取所有任务，判断当前步骤
        List<StageData> data = getMissions(templateId, taskId).getData();

        int steps = 0;
        List<TaskBo> taskBos = new ArrayList<>();
        // 取出所有任务
        for (StageData stageData : data) {
            taskBos.addAll(stageData.getTaskList());
        }
        // 根据所有任务进行排序，不分阶段
        for (TaskBo taskBo : taskBos) {
            if (taskBo.getResourceId().equals(evalMissionTemplate.getResourceId())){
                steps = taskBos.indexOf(taskBo) + 1;
            }
        }

        // 根据当前步骤，判断执行顺序
        if (steps != 1 && steps != 0) {
            List<EvalTimeLineContext> finishedSteps = evalTaskStepService.getFinishedSteps(taskId);
            if (CollectionUtils.isNotEmpty(finishedSteps)) {
                Integer stage = evalTaskStepService.getTaskStep(finishedSteps.get(finishedSteps.size() - 1).getStepId()).getStage();
                if (stage != (steps - 1)) {
                    throw new BusinessException(CrBusinessErrorCode.PREVIOUS_STEP_WAS_NOT_COMPLETED);
                }
            } else {
                throw new BusinessException(CrBusinessErrorCode.PREVIOUS_STEP_WAS_NOT_COMPLETED);
            }
        }

        return steps;
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
            rsp.setUserId(evalMissionTemplateLog.getCreateUserId());
            rsp.setName(userInfo.getName());
            rsp.setCreateTime(evalMissionTemplateLog.getCreateTime());
            rsp.setContent(evalMissionTemplateLog.getMissionResult());
            res.add(rsp);
        });
        evalNodeLogs.forEach(evalNodeLog -> {
            EvalActionProsecuteRsp rsp = new EvalActionProsecuteRsp();
            rsp.setUserId(evalNodeLog.getCreateUserId());
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

    @ApiOperation("行动监控统计人员下拉框")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @GetMapping("/action/mission/users")
    public BaseRsp<List<UserInfo>> prosecuteAction(@ApiParam(name = "resource_id", required = true, value = "资源id") @RequestParam(value = "resource_id") String resourceId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("task_id", resourceId);
        List<EvalMissionTemplateLog> templateLogs = evalMissionTemplateLogMapper.selectByMap(map);
        List<EvalNodeLog> evalNodeLogs = evalNodeLogMapper.selectByMap(map);
        List<CrConstantsConfig> all = crConstantsConfigMapper.getAll();
        Map<Integer, List<CrConstantsConfig>> constantGroup = StreamApiUtils.groupBy(all, CrConstantsConfig::getType);
        Map<String, UserInfo> userInfoMap = remoteUserService.allUsers();

        List<UserInfo> users = new ArrayList<>();
        List<String> userIds = new ArrayList<>();
        for(EvalMissionTemplateLog evalMissionTemplateLog : templateLogs){
            if(!userIds.contains(evalMissionTemplateLog.getCreateUserId())){
                userIds.add(evalMissionTemplateLog.getCreateUserId());
                users.add(userInfoMap.get(evalMissionTemplateLog.getCreateUserId()));
            }
        }
        for(EvalNodeLog evalNodeLog : evalNodeLogs){
            if(!userIds.contains(evalNodeLog.getCreateUserId())){
                userIds.add(evalNodeLog.getCreateUserId());
                users.add(userInfoMap.get(evalNodeLog.getCreateUserId()));
            }
        }
        return new BaseRsp<>(users);
    }

    @ApiOperation("行动监控统计")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @GetMapping("/action/mission/statistics")
    public BaseRsp<JSONObject> prosecuteAction(@ApiParam(name = "resource_id", required = true, value = "资源id") @RequestParam(value = "resource_id") String resourceId,
                                                                    @ApiParam(name = "user_id", value = "人员选择") @RequestParam(value = "user_id") String userId) {
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

        Map<String,Integer> taskStatusMap = new HashMap<>();
        Map<String,Integer> nodeStatusMap = new HashMap<>();
        for(EvalMissionTemplateLog evalMissionTemplateLog : templateLogs){
            if(userId != null && !evalMissionTemplateLog.getCreateUserId().equals(userId)){
                // 直接下一次循环
                continue;
            }
            String operateTypeName = missionMap.get(evalMissionTemplateLog.getOperate());
            if(taskStatusMap.get(operateTypeName) != null){
                taskStatusMap.put(operateTypeName,taskStatusMap.get(operateTypeName) + 1);
            }else{
                taskStatusMap.put(operateTypeName,1);
            }
        }
        for(EvalNodeLog evalNodeLog : evalNodeLogs){
            if(userId != null && !evalNodeLog.getCreateUserId().equals(userId)){
                // 直接下一次循环
                continue;
            }
            String operateTypeName = nodeMap.get(evalNodeLog.getOperate());
            if(nodeStatusMap.get(operateTypeName) != null){
                nodeStatusMap.put(operateTypeName,nodeStatusMap.get(operateTypeName) + 1);
            }else{
                nodeStatusMap.put(operateTypeName,1);
            }
        }
        JSONObject result = new JSONObject();
        result.put("taskStatusMap",taskStatusMap);
        result.put("nodeStatusMap",nodeStatusMap);

        return new BaseRsp<>(result);
    }


    @ApiOperation("手动执行任务")
    @RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_TEMPLATE)
    @PostMapping("/mission/manual")
    public BaseRsp<String> updateMissionManual(@RequestBody @Validated ManualExecutionReq manualExecutionReq) {
        String resourceId = manualExecutionReq.getMissionId();

        EvalMissionTemplate evalMissionTemplate = evalMissionTemplateMapper.selectById(resourceId);
        String taskId = evalMissionTemplate.getTaskId();
        EvalTask evalTask = evalTaskMapper.selectById(taskId);

        // 判断是否重复操作
        String tools = evalMissionTemplate.getTools();
        List<ToolBo> toolBos = JsonUtil.toJsonObjectArray(tools, ToolBo.class);
        Map<String, ToolBo> toolBoMap = StreamApiUtils.toMap(toolBos, ToolBo::getToolName, s -> s);
        String tool = manualExecutionReq.getTool();
        ToolBo toolBo1 = toolBoMap.get(tool);

        // 更新一次执行剩余时间
        if(TimeUtil.addSeconds(toolBo1.getExecuteTime(), 30).compareTo(new Date()) > 0){
            toolBo1.setLimitTime((int)(TimeUtil.addSeconds(toolBo1.getExecuteTime(), 30).getTime() - System.currentTimeMillis())/1000);
        } else{
            toolBo1.setLimitTime(0);
            toolBo1.setIsExecute(false);
        }
        if (Boolean.TRUE.equals(toolBo1.getIsExecute()) && toolBo1.getLimitTime() > 0) {
            return new BaseRsp<>("can't be executed repeatedly");
        }

        // 中台交互
        UserInfo userContext = UserInfoContext.getUserContext();
        evalMissionService.executeCommand(manualExecutionReq, evalTask, userContext.getUserId(), taskId);

        toolBo1.setIsExecute(true);
        toolBo1.setExecuteTime(new Date());
        toolBo1.setAction(manualExecutionReq.getCommand());
        toolBos.set(toolBos.indexOf(toolBo1), toolBo1);
        evalMissionTemplate.setTools(JsonUtil.toJsonString(toolBos));
        // 状态修改 超时单独处理--
        evalMissionTemplateMapper.updateById(evalMissionTemplate);

        return new BaseRsp<>("success");
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
                if((status.equals(MissionTemplateStatus.OVERTIME))){
                    task.setOvertimeStatus(MissionTemplateStatus.OVERTIME);
                }
                taskRes.add(task);
            }
            long count = taskRes.stream().filter(taskBo -> taskBo.getStatus() != null && (taskBo.getStatus().equals(MissionTemplateStatus.FAIL) || taskBo.getStatus().equals(MissionTemplateStatus.FINISH))).count();
            if(count == taskRes.size()){
                stageData.setStageStatus(MissionTemplateStatus.START);
            }
            stageData.setTaskList(taskRes);
            return stageData;
        }).collect(Collectors.toList());
        evalMissionTemplateService.updateBatchById(tmp);
        return new BaseRsp<>(collect);
    }

    @ApiOperation("异常数据-导出")
    @PostMapping(value = "/node/abnormal/export")
    public void userStatsDataExport(HttpServletResponse response, @RequestBody @Validated ManualExecutionReq manualExecutionReq) {
        List<EvalNodeLogRsp> evalNodeLogRsps = new ArrayList<>();

        // 操作内容
        List<EvalNodeOperateContentRsp> evalNodeOperateContentRspList = this.getNodeLogList().getData();
        Map<Integer, EvalNodeOperateContentRsp> operateMap = evalNodeOperateContentRspList.stream()
                .collect(Collectors.toMap(EvalNodeOperateContentRsp::getCode, operate -> operate));

        Map<String, UserInfo> userInfoMap = remoteUserService.allUsers();
        if (manualExecutionReq.getExportType() == 0) {
            QueryWrapper<EvalNodeLog> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(EvalNodeLog::getTaskId, manualExecutionReq.getTaskId()).orderByDesc(EvalNodeLog::getCreateTime);
            List<EvalNodeLog> nodeLogList = evalNodeLogMapper.selectList(queryWrapper);

            nodeLogList.forEach(evalNodeLog -> {
                UserInfo userInfo = userInfoMap.get(evalNodeLog.getCreateUserId());
                if(null == userInfo){
                    return;
                }
                evalNodeLog.setCreateUserId(userInfo.getUserName());
            });
            for (EvalNodeLog evalNodeLog : nodeLogList) {
                EvalNodeLogRsp evalNodeLogRsp = new EvalNodeLogRsp();
                evalNodeLogRsp.setCreateUserId(evalNodeLog.getCreateUserId());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sdf.format(evalNodeLog.getUpdateTime());
                evalNodeLogRsp.setUpdateTime(time);

                EvalNodeOperateContentRsp evalNodeOperateContentRsp = operateMap.get(evalNodeLog.getOperate());
                if (evalNodeOperateContentRsp != null) {
                    evalNodeLogRsp.setOperate(evalNodeOperateContentRsp.getOperate());
                    evalNodeLogRsp.setContent(evalNodeOperateContentRsp.getContent());
                }
                evalNodeLogRsps.add(evalNodeLogRsp);
            }
        } else if (manualExecutionReq.getExportType() == 1) {
            QueryWrapper<EvalMissionTemplateLog> queryWrapper = new QueryWrapper<>();
            List<Integer> operates = new ArrayList<>();
            operates.add(MissionTemplateStatus.FAIL);
            operates.add(MissionTemplateStatus.OVERTIME);
            queryWrapper.lambda().eq(EvalMissionTemplateLog::getTaskId, manualExecutionReq.getTaskId()).in(EvalMissionTemplateLog::getOperate, operates).orderByDesc(EvalMissionTemplateLog::getCreateTime);
            List<EvalMissionTemplateLog> nodeLogList = evalMissionTemplateLogMapper.selectList(queryWrapper);
            nodeLogList.forEach(evalNodeLog -> {
                UserInfo userInfo = userInfoMap.get(evalNodeLog.getCreateUserId());
                if(null == userInfo){
                    return;
                }
                evalNodeLog.setCreateUserId(userInfo.getUserName());
            });
            for (EvalMissionTemplateLog evalNodeLog : nodeLogList) {
                EvalNodeLogRsp evalNodeLogRsp = new EvalNodeLogRsp();
                evalNodeLogRsp.setCreateUserId(evalNodeLog.getCreateUserId());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sdf.format(evalNodeLog.getUpdateTime());
                evalNodeLogRsp.setUpdateTime(time);

                EvalNodeOperateContentRsp evalNodeOperateContentRsp = operateMap.get(evalNodeLog.getOperate());
                if (evalNodeOperateContentRsp != null) {
                    evalNodeLogRsp.setOperate(evalNodeOperateContentRsp.getOperate());
                    evalNodeLogRsp.setContent(evalNodeOperateContentRsp.getContent());
                }
                evalNodeLogRsps.add(evalNodeLogRsp);
            }
        }

        List<String> passFields = Lists.newArrayList( "roles","resourceId", "avatar");
        String sheetName = ExcelHeader.ABNORMAL.getHeader()[0];
        String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
                + CommonConstant.PERIOD + FileSunffixConstant.XLS.getDesc();

        File file = ExcelDataHandler.exportExcel(evalNodeLogRsps, filePath, sheetName,
                passFields, ExcelHeader.ABNORMAL_DATA);
        FileUtil.setResponse(file, true, response);
    }


    @ApiOperation("查看任务执行结果")
    @GetMapping("/mission/result")
    public BaseRsp<String> getMissionResult(@ApiParam(name = "mission_id", value = "任务id", required = true) @RequestParam("mission_id") String missionId,
                                            @ApiParam(name = "task_id", value = "测评任务id", required = true) @RequestParam("task_id") String taskId) {
        Map<String, Object> map = new HashMap<>();
        map.put("mission_id", missionId);
        map.put("task_id", taskId);
        List<EvalMissionTemplateLog> evalMissionTemplateLogs = evalMissionTemplateLogMapper.selectByMap(map);

        String result = null;
        if (!evalMissionTemplateLogs.isEmpty()) {
            for (EvalMissionTemplateLog evalMissionTemplateLog : evalMissionTemplateLogs) {
                if (StringUtils.isNotEmpty(evalMissionTemplateLog.getMissionResult())) {
                    result = evalMissionTemplateLog.getMissionResult();
                    break;
                }
            }
        }
        return new BaseRsp<>(result);
    }


}