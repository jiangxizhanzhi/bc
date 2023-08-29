package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.rsp.EvalEventCountBar;
import com.cyberpeace.cr.crbase.controller.rsp.EvalEventCountRingGraph;
import com.cyberpeace.cr.crbase.controller.rsp.EvalEventStatusCount;
import com.cyberpeace.cr.crbase.controller.rsp.EvalRoleEventActivity;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.CrNoticeMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleAssetNodeCopyMapper;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalResultListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalResultListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoleEventActivityListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoleEventPercentRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoleScoreListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalResult;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRoleScore;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalRole;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.EvalRecordService;
import com.cyberpeace.cr.evaluation.service.EvalResultService;
import com.cyberpeace.cr.evaluation.service.EvalRoleScoreService;
import com.cyberpeace.situation.constant.ws.ActionTypeZHL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    private CrEvalRoleMapper crEvalRoleMapper;

    @Autowired
    private CrNoticeMapper crNoticeMapper;

    @Autowired
    private CrEvalTrainTaskMapper crEvalTrainTaskMapper;

    @Autowired
    private CrEvalTaskLogMapper crEvalTaskLogMapper;

    @Autowired
    private CrRoleAssetNodeCopyMapper crRoleAssetNodeCopyMapper;

    @Autowired
    private EvalResultService evalResultService;

    @Autowired
    private EvalRoleScoreService evalRoleScoreService;

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
        Integer finalScore = evalRecord.getFinalScore();
        Integer indexScore = evalRecord.getIndexScore();
        Integer score = evalRecord.getScore();
        evalRecord.setStatus(EvalRecord.Status.RUNNING.getCode());
        evalRecord.setScore(null);
        evalRecord.setFinalScore(null);
        evalRecord.setIndexScore(null);
        evalRecordMapper.updateById(evalRecord);
        evalRecordService.reRun(requestData, evalRecord, originalStatus,finalScore, score, indexScore);
        return new BaseRsp<>(evalRecord);
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
            value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId, @RequestParam("role") @NotEmpty Integer type) {

        EvalRecord evalRecord = evalRecordService.getById(resourceId);
        List<EvalRoleScoreListRsp> res = new ArrayList<>();
        String templateId = evalRecord.getTemplateId();
        EvalTemplate evalTemplate = evalTemplateMapper.selectById(templateId);
        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setRoomId(evalTemplate.getRedVsBlueId());

        List<CrEvalRole> crRoles = crEvalRoleMapper.selectCrRoleByRoomId(roleQuery);
        crRoles = crRoles.stream().filter(s-> s.getRoleGroup().equals(type)).collect(Collectors.toList());
        if(evalRecord.getStatus().equals(EvalRecord.Status.END.getCode()) || evalRecord.getStatus().equals(EvalRecord.Status.EXCEPTION.getCode())){
            LambdaQueryWrapper<EvalRoleScore> query = new LambdaQueryWrapper<>();
            query.eq(EvalRoleScore::getRecord, resourceId);
            query.eq(EvalRoleScore::getRoleGroup, type);
            List<EvalRoleScore> evalRoleScores = evalRoleScoreService.getBaseMapper().selectList(query);
            Map<String, EvalRoleScore> scoreMap = StreamApiUtils.toMap(evalRoleScores, EvalRoleScore::getRoleId, s -> s);
            for(CrEvalRole crRole:crRoles){
                EvalRoleScoreListRsp rsp = new EvalRoleScoreListRsp();
                rsp.setRole(crRole.getRoleGroup());
                rsp.setName(crRole.getName());
                if(scoreMap.containsKey(crRole.getResourceId())){
                    EvalRoleScore evalRoleScore = scoreMap.get(crRole.getResourceId());
                    rsp.setScore(evalRoleScore.getScore());
                }
                res.add(rsp);

            }
        }
        return new BaseRsp<>(res);
    }


    private List<CrEvalRole> getCrRoles(String redVsBlueId) {
        LambdaQueryWrapper<CrEvalRole> query = new LambdaQueryWrapper<>();
        query.eq(CrEvalRole::getRoomId, redVsBlueId);
        List<CrEvalRole> crRoles = crEvalRoleMapper.selectList(query);
        return crRoles.stream().filter(crRole -> crRole.getRoleGroup().equals(CrRole.RbRoleGroup.RED.getCode()) ||
                crRole.getRoleGroup().equals(CrRole.RbRoleGroup.BLUE.getCode())).collect(Collectors.toList());
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

    private EvalTemplate getRoomById(String resourceId) {
        EvalRecord evalRecord = evalRecordMapper.selectById(resourceId);
        if (null == evalRecord) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return evalTemplateMapper.selectById(evalRecord.getTemplateId());
    }

    @ApiOperation("评估结果详情-红蓝队实时消息-折线图")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/event/msg/")
    public BaseRsp<EvalRoleEventActivityListRsp> getEventActivity(@ApiParam(name = "id",
            value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId) {
        EvalRoleEventActivityListRsp res = new EvalRoleEventActivityListRsp();
        List<String> dates = new ArrayList<>();
        List<Integer> redCount = new ArrayList<>();
        List<Integer> blueCount = new ArrayList<>();

        String redVsBlueId = getRoomId(resourceId);

        List<Integer> activeTimeList = CrTaskLog.ActionCode.ACTIVE_TIME_LIST;
        List<EvalRoleEventActivity> crTaskLogs = crEvalTaskLogMapper.getReleaseIdsByRoomIdAndUsers(redVsBlueId,activeTimeList);

        for(EvalRoleEventActivity crTaskLog:crTaskLogs){
            dates.add(new SimpleDateFormat("HH:mm:ss").format(crTaskLog.getCreateTime()));
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
        List<String> dates = new ArrayList<>();
        List<Integer> redCount = new ArrayList<>();
        List<Integer> blueCount = new ArrayList<>();

        String redVsBlueId = getRoomId(resourceId);
        List<CrEvalRole> crRoles = getCrRoles(redVsBlueId);
        Map<Integer, List<CrEvalRole>> roleMap = StreamApiUtils.groupBy(crRoles, CrEvalRole::getRoleGroup);
        List<CrEvalRole> blueRoles = roleMap.get(CrRole.RbRoleGroup.BLUE.getCode());
        List<CrEvalRole> redRoles = roleMap.get(CrRole.RbRoleGroup.RED.getCode());

        List<CrRoleNode> redNode= getRoleNodeCount(redRoles);
        List<String> redDeviceIds = StreamApiUtils.getColumn(redNode, CrRoleNode::getId).stream().distinct().collect(Collectors.toList());
        List<CrRoleNode> blueNode = getRoleNodeCount(blueRoles);
        List<String> blueDeviceIds = StreamApiUtils.getColumn(blueNode, CrRoleNode::getId).stream().distinct().collect(Collectors.toList());

        LambdaQueryWrapper<CrRoleAssetNodeCopy> query = new LambdaQueryWrapper<>();
        query.eq(CrRoleAssetNodeCopy::getRoomId, redVsBlueId);
        List<CrRoleAssetNodeCopy> crRoleAssetNodes = crRoleAssetNodeCopyMapper.selectList(query);

        for(CrRoleAssetNodeCopy roleAssetNode:crRoleAssetNodes){
            if(roleAssetNode.getOriginalRoleType().equals(CrRole.RbRoleGroup.RED.getCode()) && roleAssetNode.getRoleType().equals(CrRole.RbRoleGroup.BLUE.getCode())){
                redDeviceIds.remove(roleAssetNode.getDeviceId());
                redCount.add(redDeviceIds.size());
                if (!blueDeviceIds.contains(roleAssetNode.getDeviceId())) {
                    blueDeviceIds.add(roleAssetNode.getDeviceId());
                }
                blueCount.add(blueDeviceIds.size());
                dates.add(new SimpleDateFormat("HH:mm:ss").format(roleAssetNode.getCreateTime()));
            } else if(roleAssetNode.getOriginalRoleType().equals(CrRole.RbRoleGroup.BLUE.getCode()) && roleAssetNode.getRoleType().equals(CrRole.RbRoleGroup.RED.getCode())){
                if(!redDeviceIds.contains(roleAssetNode.getDeviceId())){
                    redDeviceIds.add(roleAssetNode.getDeviceId());
                }
                redCount.add(redDeviceIds.size());
                blueDeviceIds.remove(roleAssetNode.getDeviceId());
                blueCount.add(blueDeviceIds.size());
                dates.add(new SimpleDateFormat("HH:mm:ss").format(roleAssetNode.getCreateTime()));
            }
        }
        res.setCreatTime(dates);
        res.setBlueCount(blueCount);
        res.setRedCount(redCount);
        return new BaseRsp<>(res);

    }


    private List<CrRoleNode> getRoleNodeCount(List<CrEvalRole> redRoles) {
        List<CrRoleNode> assetNodes = new ArrayList<>();
        for(CrEvalRole redRole: redRoles){
            CrRoleNodesData nodeData = redRole.getNodeData();
            assetNodes.addAll(nodeData.getAssetNodes());
        }
        return assetNodes.stream().distinct().collect(Collectors.toList());
    }

    @ApiOperation("评估结果详情-攻防事件-柱状图")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/event/bar")
    public BaseRsp<List<EvalRoleEventPercentRsp>> getEventBar(@ApiParam(name = "id",
            value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId) {
        String redVsBlueId = getRoomId(resourceId);
        List<EvalEventCountBar> taskByRoomIdAndUsers = crEvalTaskLogMapper.getTaskByRoomIdAndType(redVsBlueId);
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
    public BaseRsp<List<EvalEventCountBar>> getEventCount(
            @ApiParam(name = "id",value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId, @RequestParam("role") @NotEmpty Integer type) {
        EvalTemplate evalTemplate = getRoomById(resourceId);
        return new BaseRsp<>(evalResultService.getEventCount(evalTemplate.getResourceId(), type));
    }

    @ApiOperation("评估结果详情-攻防事件-环形图")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/event/pencent")
    public BaseRsp<List<EvalEventCountRingGraph>> getEventPencent(

            @ApiParam(name = "id",
            value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId, @RequestParam("role") @NotEmpty Integer type) {
        EvalTemplate evalTemplate = getRoomById(resourceId);
        return new BaseRsp<>(evalResultService.getEventPercent(evalTemplate.getResourceId(), type));
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
        List<EvalEventStatusCount> taskByRoomIdAndUsers = crEvalTrainTaskMapper.getTaskByRoomIdAndUsers(redVsBlueId);
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
