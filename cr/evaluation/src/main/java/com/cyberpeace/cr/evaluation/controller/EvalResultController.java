package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalResultListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalResultListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalResult;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalRecordMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalResultMapper;
import com.cyberpeace.cr.evaluation.service.EvalRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("获取靶场测评实例评估结果（分页）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/list")
    public BaseRsp<ListRsp<EvalResultListReq>> list(EvalResultListReq queryParams) {

        String startTime = queryParams.getStartTime();
        String endTime = queryParams.getEndTime();
        Page<EvalResultListRsp> evalResultListRspPage = evalResultMapper.pageList(queryParams.toPageParam(), startTime, endTime, queryParams.getType(), queryParams.getPattern());
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
        reRun(requestData, evalRecord);
        evalRecord.setStatus(EvalRecord.Status.RUNNING.getCode());
        evalRecordMapper.updateById(evalRecord);
        return new BaseRsp<>(evalRecord);
    }


    @Async
    public Integer reRun(EvalRecordAddReq requestData, EvalRecord evalRecord) {

        Integer finalScore = requestData.getIndexScore() * evalRecord.getScore();
        EvalResult evalResult = new EvalResult();
        evalResult.setRecord(requestData.getResourceId());
        evalResult.setWeight(evalRecord.getScore());
        evalResult.setIndexScore(requestData.getIndexScore());
        evalResult.setFinalScore(finalScore);
        evalResult.setType(EvalResult.Type.SYSTEM_RUN.getCode());
        evalResultMapper.insert(evalResult);

        evalRecord.setIndexScore(requestData.getIndexScore());
        evalRecord.setFinalScore(finalScore);
        evalRecordMapper.updateById(evalRecord);
        return finalScore;
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
}
