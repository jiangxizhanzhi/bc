package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskLog;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskLogMapper;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalResultListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoleEventActivityListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoleScoreListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalResultListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalResult;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalScore;
import com.cyberpeace.cr.evaluation.dao.mapper.CrEvalScoreMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalRecordMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalResultMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalRecordService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @GetMapping("/event/score")
    public BaseRsp<List<EvalRoleEventActivityListRsp>> getEventActivity(@ApiParam(name = "id",
            value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId) {
        List<EvalRoleEventActivityListRsp> res = new ArrayList<>();

        String redVsBlueId = getRoomId(resourceId);
        List<CrRole> redRoles = getCrRoles(redVsBlueId);

        LambdaQueryWrapper<CrTaskLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CrTaskLog::getRoomId, redVsBlueId);
        redRoles.forEach(crRole -> {
            List<String> ids = JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class);
            List<CrTaskLog> crTaskLogs = crTaskLogMapper.getReleaseIdsByRoomIdAndUsers(redVsBlueId, ids);
            EvalRoleEventActivityListRsp rsp = new EvalRoleEventActivityListRsp();
//            rsp.setCount(cr);
        });
//        Map<Integer, CrTaskLog> taskLogMap = StreamApiUtils.toMap(crTaskLogs, CrTaskLog::getUserId, s -> s);
//        CrTaskLog crTaskLog = taskLogMap.get(CrTaskLog.ActionCode.SUBMIT.getCode());
        // 完成答题
//        CrTaskLog crTaskLog = taskLogMap.get(CrTaskLog.ActionCode.SUBMIT.getCode());
        return new BaseRsp<>(res);

    }

}
