package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.rsp.EvalEventCountBar;
import com.cyberpeace.cr.crbase.controller.rsp.EvalEventStatusCount;
import com.cyberpeace.cr.crbase.controller.rsp.EvalRoleEventActivity;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.*;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalResultListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalResultListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoleEventActivityListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoleEventPercentRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoleScoreListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalResult;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalScore;
import com.cyberpeace.cr.evaluation.dao.mapper.CrEvalScoreMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalRecordMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalResultMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalRecordService;
import com.cyberpeace.situation.constant.ws.ActionTypeZHL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.stream.Collectors;

import static com.cyberpeace.common.util.EnumUtils.getListCode;

@Api(tags = "靶场测评-评估结果")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/result")
@Slf4j
public class EvalResultController {

    @Autowired
    private EvalRecordService evalRecordService;

    @Autowired
    private EvalRecordMapper evalRecordMapper;

    @Autowired
    private EvalResultMapper evalResultMapper;

    @Autowired
    private EvalTemplateMapper evalTemplateMapper;

    @Autowired
    private CrRoleMapper crRoleMapper;

    @Autowired
    private CrEvalScoreMapper crEvalScoreMapper;

    @Autowired
    private CrTaskLogMapper crTaskLogMapper;

    @Autowired
    private CrNoticeMapper crNoticeMapper;

    @Autowired
    private CrTaskMapper crTaskMapper;

    @Autowired
    private CrRoleAssetNodeMapper crRoleAssetNodeMapper;

    @Autowired
    private CrConstantsService crConstantsService;

    @ApiOperation("获取靶场测评实例评估结果（分页）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/list")
    public BaseRsp<ListRsp<EvalResultListRsp>> list(EvalResultListReq queryParams) {

        String startTime = queryParams.getStartTime();
        String endTime = queryParams.getEndTime();
        Page<EvalResultListRsp> evalResultListRspPage = evalResultMapper.pageList(queryParams.toPageParam(),
                startTime, endTime, queryParams.getType(), queryParams.getPattern(), queryParams.getRecord());
        return new BaseRsp<>(PageUtil.getListResult(evalResultListRspPage));
    }

    @ApiOperation("人工改判")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PatchMapping("/manual/run")
    public BaseRsp<EvalRecord> updateFinalScore(@RequestBody EvalRecordAddReq requestData) {
        EvalRecord evalRecord = checkScore(requestData.getFinalScore(), requestData.getResourceId());
        EvalResult evalResult = new EvalResult();
        evalResult.setRecord(requestData.getResourceId());
        evalResult.setWeight(evalRecord.getScore());
        evalResult.setIndexScore(evalRecord.getIndexScore());
        evalResult.setFinalScore(requestData.getFinalScore());
        evalResult.setType(EvalResult.Type.MANUAL_SYSTEM.getCode());
        evalResultMapper.insert(evalResult);
        evalRecord.setFinalScore(requestData.getFinalScore());
        evalRecordMapper.updateById(evalRecord);
        return new BaseRsp<>(evalRecord);
    }

    @ApiOperation("重新改判")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PatchMapping("/system/run")
    public BaseRsp<EvalRecord> reviseFinalScore(@RequestBody EvalRecordAddReq requestData) {
        EvalRecord evalRecord = checkScore(requestData.getIndexScore(), requestData.getResourceId());
        Integer originalStatus = evalRecord.getStatus();
        evalRecord.setStatus(EvalRecord.Status.RUNNING.getCode());
        evalRecordMapper.updateById(evalRecord);
        reRun(requestData, evalRecord, originalStatus);
        return new BaseRsp<>(evalRecord);
    }


    @Async
    public void reRun(EvalRecordAddReq requestData, EvalRecord evalRecord, Integer originalStatus) {

        EvalResult evalResult = new EvalResult();
        evalResult.setRecord(requestData.getResourceId());
        evalResult.setWeight(evalRecord.getScore());
        evalResult.setIndexScore(requestData.getIndexScore());
        evalResult.setFinalScore(evalRecord.getFinalScore());
        evalResult.setType(EvalResult.Type.SYSTEM_RUN.getCode());
        evalResultMapper.insert(evalResult);

        evalRecord.setIndexScore(requestData.getIndexScore());
        evalRecord.setStatus(originalStatus);
        evalRecordMapper.updateById(evalRecord);
    }

    private EvalRecord checkScore(Integer score, String resourceId) {
        int lenMin = Integer.parseInt(ConfigUtil.getContextProperty("cr.str.len.zero"));
        int categoryMax = Integer.parseInt(ConfigUtil.getContextProperty("cr.str.len.score.max"));
        if (null != score) {
            CrCommonValidator.validateRange(score, lenMin, categoryMax, CrBusinessErrorCode.MAX_SCORE_10000);
        }
        EvalRecord evalRecord = evalRecordService.getById(resourceId);
        if(null == evalRecord){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return evalRecord;
    }

    @ApiOperation("评估结果详情-红蓝队评分-柱状图")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/role/score")
    public BaseRsp<List<EvalRoleScoreListRsp>> getScoreByRole(@ApiParam(name = "id",
            value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId) {

        String redVsBlueId = getRoomId(resourceId);

        List<CrRole> redRoles = getCrRoles(redVsBlueId);
        List<EvalRoleScoreListRsp> res = new ArrayList<>();

        for(CrRole redRole:redRoles){
            List<String> ids = JsonUtil.toJsonObjectArray(redRole.getUserIds(), String.class);
            List<CrEvalScore> crEvalScores = crEvalScoreMapper.getByRoomIdAndUsers(redVsBlueId, ids);
            List<Integer> scores = StreamApiUtils.getColumn(crEvalScores, CrEvalScore::getFinalScore);
            int sum = scores.stream().mapToInt(Integer::intValue).sum();
            EvalRoleScoreListRsp rsp = new EvalRoleScoreListRsp();
            rsp.setRole(redRole.getResourceId());
            rsp.setName(redRole.getName());
            rsp.setScore(sum);
            res.add(rsp);
        }
        return new BaseRsp<>(res);
    }


    private List<CrRole> getCrRoles(String redVsBlueId) {
        LambdaQueryWrapper<CrRole> query = new LambdaQueryWrapper<>();
        query.eq(CrRole::getRoomId, redVsBlueId);
        List<CrRole> crRoles = crRoleMapper.selectList(query);
        List<CrRole> redRoles = crRoles.stream().filter(crRole -> crRole.getRoleGroup().equals(CrRole.RbRoleGroup.RED.getCode()) ||
                crRole.getRoleGroup().equals(CrRole.RbRoleGroup.BLUE.getCode())).collect(Collectors.toList());
        return redRoles;
    }

    private String getRoomId(String resourceId) {
        EvalRecord evalRecord = evalRecordMapper.selectById(resourceId);
        if (null == evalRecord) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        EvalTemplate evalTemplate = evalTemplateMapper.selectById(evalRecord.getTemplateId());
        String redVsBlueId = evalTemplate.getRedVsBlueId();
        return redVsBlueId;
    }

    @ApiOperation("评估结果详情-红蓝队实时消息-折线图")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/event/msg/")
    public BaseRsp<EvalRoleEventActivityListRsp> getEventActivity(@ApiParam(name = "id",
            value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId) {
        EvalRoleEventActivityListRsp res = new EvalRoleEventActivityListRsp();
        List<Date> dates = new ArrayList<>();
        List<Integer> redCount = new ArrayList<>();
        List<Integer> blueCount = new ArrayList<>();

        String redVsBlueId = getRoomId(resourceId);

        List<EvalRoleEventActivity> crTaskLogs = crTaskLogMapper.getReleaseIdsByRoomIdAndUsers(redVsBlueId);

        for(EvalRoleEventActivity crTaskLog:crTaskLogs){
            dates.add(crTaskLog.getCreateTime());
            Integer count = crTaskLog.getCount();
            if(crTaskLog.getRoleGroup().equals(CrRole.RbRoleGroup.RED.getCode())){
                redCount.add(count);
                blueCount.add(0);

            } else{
                redCount.add(0);
                blueCount.add(count);
            }
        }
        res.setCreatTime(dates);
        res.setBlueCount(blueCount);
        res.setRedCount(redCount);
        return new BaseRsp<>(res);

    }

    @ApiOperation("评估结果详情-节点资产变化-折线图")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/event/node/")
    public BaseRsp<EvalRoleEventActivityListRsp> getNodeActivity(@ApiParam(name = "id",
            value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId) {
        EvalRoleEventActivityListRsp res = new EvalRoleEventActivityListRsp();
        List<Date> dates = new ArrayList<>();
        List<Integer> redCount = new ArrayList<>();
        List<Integer> blueCount = new ArrayList<>();

        String redVsBlueId = getRoomId(resourceId);
        LambdaQueryWrapper<CrRoleAssetNode> query = new LambdaQueryWrapper<>();
        query.eq(CrRoleAssetNode::getRoomId, redVsBlueId);
        List<CrRoleAssetNode> crRoleAssetNodes = crRoleAssetNodeMapper.selectList(query);

        for(CrRoleAssetNode roleAssetNode:crRoleAssetNodes){
            dates.add(roleAssetNode.getCreateTime());
            Integer count = roleAssetNode.getRoleType();
            if(roleAssetNode.getRoleType().equals(CrRole.RbRoleGroup.RED.getCode())){
                redCount.add(count);
                blueCount.add(0);

            } else{
                redCount.add(0);
                blueCount.add(count);
            }
        }
        res.setCreatTime(dates);
        res.setBlueCount(blueCount);
        res.setRedCount(redCount);
        return new BaseRsp<>(res);

    }

    @ApiOperation("评估结果详情-攻防事件-柱状图")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/event/bar")
    public BaseRsp<List<EvalRoleEventPercentRsp>> getEventBar(@ApiParam(name = "id",
            value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId) {
        String redVsBlueId = getRoomId(resourceId);
        List<EvalEventCountBar> taskByRoomIdAndUsers = crTaskMapper.getTaskByRoomIdAndType(redVsBlueId);
        Map<Integer, EvalEventCountBar> barMap = StreamApiUtils.toMap(taskByRoomIdAndUsers, EvalEventCountBar::getType, s -> s);
        List<EvalRoleEventPercentRsp> res = new ArrayList<>();
        List<Integer> listCode = getListCode(ActionTypeZHL.class);

        for( Integer tmp:listCode){
            EvalRoleEventPercentRsp rsp = new EvalRoleEventPercentRsp();
            rsp.setCount(0);
            if(barMap.containsKey(tmp)){
                EvalEventCountBar evalEventCountBar = barMap.get(tmp);
                rsp.setCount(evalEventCountBar.getCount());
            }
            String eventTypeStr = EnumUtils.getEnumDescByCode(tmp, ActionTypeZHL.class);
            if(tmp > ActionTypeZHL.ZHL_ATTACK6.getCode()){
                rsp.setRole(CrRole.RbRoleGroup.BLUE.getCode());
            } else {
                rsp.setRole(CrRole.RbRoleGroup.RED.getCode());
            }
            rsp.setName(eventTypeStr);
            res.add(rsp);
        }
        return new BaseRsp<>(res);

    }

    @ApiOperation("评估结果详情-攻防事件-雷达图")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/event/count")
    public BaseRsp<List<EvalEventCountBar>> getEventCount(@ApiParam(name = "id",
            value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId, @RequestParam("role") @NotEmpty Integer type) {
        List<EvalEventCountBar> res = new ArrayList<>();
        String redVsBlueId = getRoomId(resourceId);
        List<EvalEventCountBar> taskByRoomIdAndUsers = crTaskMapper.getTaskByRoomIdAndType(redVsBlueId);
        Map<Integer, EvalEventCountBar> barMap = StreamApiUtils.toMap(taskByRoomIdAndUsers, EvalEventCountBar::getType, s -> s);
        List<Integer> listCode = getListCode(ActionTypeZHL.class);
        if(type.equals(CrRole.RbRoleGroup.RED.getCode())){
            listCode = listCode.stream().filter(s->s<=ActionTypeZHL.ZHL_ATTACK6.getCode()).collect(Collectors.toList());

        } else if(type.equals(CrRole.RbRoleGroup.BLUE.getCode())){
            listCode = listCode.stream().filter(s->s>ActionTypeZHL.ZHL_ATTACK6.getCode()).collect(Collectors.toList());;
        } else {
            return new BaseRsp<>(res);
        }
        for(Integer tmp:listCode){
            EvalEventCountBar rsp = new EvalEventCountBar();
            rsp.setCount(0);
            if(barMap.containsKey(tmp)){
                EvalEventCountBar evalEventCountBar = barMap.get(tmp);
                rsp.setCount(evalEventCountBar.getCount());
            }
            String eventTypeStr = EnumUtils.getEnumDescByCode(tmp, ActionTypeZHL.class);
            rsp.setType(tmp);
            rsp.setEventName(eventTypeStr);
            res.add(rsp);
        }

        return new BaseRsp<>(res);

    }

    @ApiOperation("评估结果详情-攻防事件-雷达图")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/event/pencent")
    public BaseRsp<List<EvalEventCountBar>> getEventPencent(@ApiParam(name = "id",
            value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId, @RequestParam("role") @NotEmpty Integer type) {
        List<EvalEventCountBar> res = new ArrayList<>();
        String redVsBlueId = getRoomId(resourceId);
        List<EvalEventCountBar> taskByRoomIdAndUsers = crTaskMapper.getTaskByRoomIdAndType(redVsBlueId);
        Map<Integer, EvalEventCountBar> barMap = StreamApiUtils.toMap(taskByRoomIdAndUsers, EvalEventCountBar::getType, s -> s);
        List<CrConstantsConfig> crConstantsConfigList;
        if(type.equals(CrRole.RbRoleGroup.RED.getCode())){
            crConstantsConfigList = crConstantsService.getByType(-1);

        } else if(type.equals(CrRole.RbRoleGroup.BLUE.getCode())){
            crConstantsConfigList = crConstantsService.getByType(-2);
        } else {
            return new BaseRsp<>(res);
        }
        Integer sum = 0;
        for(CrConstantsConfig tmp:crConstantsConfigList){
            Integer tmpType = tmp.getType();
            EvalEventCountBar rsp = new EvalEventCountBar();
            rsp.setCount(0);
            if(barMap.containsKey(tmpType)){
                EvalEventCountBar evalEventCountBar = barMap.get(tmpType);
                Integer count = evalEventCountBar.getCount();
                sum += count;
                rsp.setCount(count);
            }
            String eventTypeStr = EnumUtils.getEnumDescByCode(tmpType, ActionTypeZHL.class);
            rsp.setType(type);
            rsp.setEventName(eventTypeStr);
            res.add(rsp);
        }
        Integer finalSum = sum;
        res.forEach(item -> item.setCount(finalSum.equals(0) ? 0 : item.getCount()/ finalSum));

        return new BaseRsp<>(res);

    }


    @ApiOperation("评估结果详情-任务公告-环形图")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/event/notices")
    public BaseRsp<List<EvalEventStatusCount>> getEventOtherPercent(@ApiParam(name = "id",
            value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId) {
        LambdaQueryWrapper<CrNotice> queryWrapper = new LambdaQueryWrapper<>();
        String redVsBlueId = getRoomId(resourceId);
        queryWrapper.eq(CrNotice::getRoomId, redVsBlueId);
        List<CrNotice> crNotices = crNoticeMapper.selectList(queryWrapper);
        List<EvalEventStatusCount> taskByRoomIdAndUsers = crTaskMapper.getTaskByRoomIdAndUsers(redVsBlueId);
        List<EvalEventStatusCount> res = new ArrayList<>();
        Map<Integer, Integer> toMap = StreamApiUtils.toMap(taskByRoomIdAndUsers, EvalEventStatusCount::getStatus, EvalEventStatusCount::getCount);
        List<Integer> codes = Arrays.asList(CrTask.TaskStatus.CLOSE.getCode(), CrTask.TaskStatus.RELEASED.getCode());
        for(Integer code:codes){
            EvalEventStatusCount statusCount = new EvalEventStatusCount();
            statusCount.setStatus(code);
            statusCount.setCount(0);
            if(toMap.containsKey(code)){
                statusCount.setCount(toMap.get(code));
            }
            res.add(statusCount);
        }
        EvalEventStatusCount statusCount = new EvalEventStatusCount();
        statusCount.setStatus(0);
        statusCount.setCount(crNotices.size());
        res.add(statusCount);
        return new BaseRsp<>(res);
    }

}
