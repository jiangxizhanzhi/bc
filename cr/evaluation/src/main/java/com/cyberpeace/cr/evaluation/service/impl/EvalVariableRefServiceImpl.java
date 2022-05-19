package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.exception.evaluate.RemoteException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableGraphRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalVariableRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmRefService;
import com.cyberpeace.cr.evaluation.service.EvalProbeDataService;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefProbeDataService;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefService;
import com.cyberpeace.cr.evaluation.service.context.EvalVariableRefProbeDataContext;
import com.cyberpeace.integratation.model.evaluation.*;
import com.cyberpeace.integratation.service.RemotePythonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EvalVariableRefServiceImpl extends ServiceImpl<EvalVariableRefMapper, EvalVariableRef> implements EvalVariableRefService {
    @Autowired
    private EvalProbeDataService evalProbeDataService;

    @Autowired
    private EvalVariableRefProbeDataService evalVariableRefProbeDataService;

    @Autowired
    private RemotePythonService remotePythonService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private EvalAlgorithmRefService evalAlgorithmRefService;

    @Override
    public String startVariableAlgorithm(EvalVariableRef evalVariableRef,
                                       Map<String, EvalAlgorithmRef> evalAlgorithmRefMap,
                                       EvalRecord evalRecord,
                                       EvalTask evalTask) {
        return startFormat(evalVariableRef, evalAlgorithmRefMap, evalRecord, evalTask);
    }

    /**
     * 采集变量项目执行失败, 记录
     * @param evalVariableRef
     * @param evalRecord
     * @param msg
     */
    private String exceptionBefore(EvalVariableRef evalVariableRef, EvalRecord evalRecord, String msg) {
        return msg;
    }

    private String startFormat(EvalVariableRef evalVariableRef, Map<String, EvalAlgorithmRef> evalAlgorithmRefMap, EvalRecord evalRecord, EvalTask evalTask) {
        EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMap.get(evalVariableRef.getFormatAlgorithmId());
        if (!evalVariableRef.getProgress().equals(EvalVariableRef.Progress.FORMATTING.getCode())) {
            return startMatch(evalVariableRef, evalAlgorithmRefMap, evalRecord, evalTask);
        }

        // 变量项探针采集数据  todo 分页执行
        List<EvalProbeData> evalProbeDataList = evalProbeDataService.lambdaQuery()
                .eq(EvalProbeData::getSceneId, evalTask.getMpSceneId())
                .eq(EvalProbeData::getDeviceId, evalVariableRef.getDeviceId())
                .eq(EvalProbeData::getProbeId, evalVariableRef.getProbeId())
                .ge(EvalProbeData::getCollectionTime, evalRecord.getStartTime())
                .le(EvalProbeData::getCollectionTime, evalRecord.getEndTime())
                .list();

        // 时间范围内无采集数据情况
        if (evalProbeDataList.isEmpty()) {
            return exceptionBefore(evalVariableRef, evalRecord, BusinessException.getMessageByLanguage(CrBusinessErrorCode.VARIABLE_DID_NOT_GET_PROBE_DATA));
        }

        // 构建格式化算法数据
        FormatData data = new FormatData();
        data.setData(evalProbeDataList.stream().map(EvalProbeData::getContent).collect(Collectors.toList()));

        // 运行匹配算法
        List<String> formatReturn;
        evalVariableRef.setFormatStartTime(new Date());
        try {
            formatReturn = remotePythonService.formatPerform(
                    evalAlgorithmRef.getResourceId(),
                    evalAlgorithmRef.getCode(),
                    data,
                    evalAlgorithmRefService.buildOtherArguments(evalVariableRef.getFormatParams())
            );
        } catch (RemoteServiceException e) {
            return exceptionBefore(
                    evalVariableRef, evalRecord,
                    String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.VARIABLE_FORMAT_EXECUTE_FAILED), evalVariableRef.getName())
            );
        }
        evalVariableRef.setFormatEndTime(new Date());

        List<EvalVariableRefProbeData> evalVariableRefProbeDataList = Lists.newArrayList();
        for (int i = 0; i < formatReturn.size(); i++) {
            EvalVariableRefProbeData object = new EvalVariableRefProbeData();
            EvalProbeData originData = evalProbeDataList.get(i);
            object.setProbeDataId(originData.getId());
            object.setVariableId(evalVariableRef.getResourceId());
            object.setFormattedValue(formatReturn.get(i));
            object.setStatus(EvalVariableRefProbeData.Status.NONE.getCode());
            object.setTime(originData.getCollectionTime());

            evalVariableRefProbeDataList.add(object);
        }

        evalVariableRef.setProgress(EvalVariableRef.Progress.MATCHING.getCode());

        transactionTemplate.execute(status -> {
            evalVariableRefProbeDataService.saveBatch(evalVariableRefProbeDataList);
            return SqlHelper.retBool(getBaseMapper().updateById(evalVariableRef));
        });

        return startMatch(evalVariableRef, evalAlgorithmRefMap, evalRecord, evalTask);
    }

    private String startMatch(EvalVariableRef evalVariableRef, Map<String, EvalAlgorithmRef> evalAlgorithmRefMap, EvalRecord evalRecord, EvalTask evalTask) {
        EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMap.get(evalVariableRef.getFormatAlgorithmId());
        if (!evalVariableRef.getProgress().equals(EvalVariableRef.Progress.MATCHING.getCode())) {
            return startMark(evalVariableRef, evalAlgorithmRefMap, evalRecord, evalTask);
        }

        List<EvalVariableRefProbeDataContext> evalVariableRefProbeDataContextList = evalVariableRefProbeDataService
                .variableProbeDataResult(evalVariableRef.getResourceId());
        MatchData data = new MatchData();

        // 构建匹配算法数据
        data.setBaseTime(evalVariableRefProbeDataContextList.get(1).getTime().getTime());
        data.setData(Lists.newArrayList());
        evalVariableRefProbeDataContextList.forEach(variableRefProbeDataContext -> {
            MatchItemData itemData = new MatchItemData();
            itemData.setT(variableRefProbeDataContext.getTime().getTime() - data.getBaseTime());
            itemData.setV(variableRefProbeDataContext.getFormattedValue());

            data.getData().add(itemData);
        });

        // 运行匹配算法
        List<Integer> matchReturn;
        evalVariableRef.setMatchStartTime(new Date());
        try {
            matchReturn = remotePythonService.matchPerform(
                    evalAlgorithmRef.getResourceId(),
                    evalAlgorithmRef.getCode(),
                    data,
                    evalAlgorithmRefService.buildOtherArguments(evalVariableRef.getFormatParams())
            );
        } catch (RemoteServiceException e) {
            return exceptionBefore(
                    evalVariableRef, evalRecord,
                    String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.VARIABLE_MATCH_EXECUTE_FAILED), evalVariableRef.getName())
            );
        }
        evalVariableRef.setMatchEndTime(new Date());

        List<EvalVariableRefProbeData> evalVariableRefProbeDataList = evalVariableRefProbeDataService.lambdaQuery()
                .eq(EvalVariableRefProbeData::getVariableId, evalVariableRef.getResourceId())
                .list();

        // 执行失败, 返回值异常
        if (matchReturn.size() != evalVariableRefProbeDataList.size()) {
            return exceptionBefore(
                    evalVariableRef, evalRecord,
                    String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.MATCH_ALGORITHM_RESULT_IS_INVALID), evalVariableRef.getName())
            );
        }

        for (int i = 0; i < evalVariableRefProbeDataList.size(); i++) {
            // 避免返回值存在[0, 1]外的值
            if (matchReturn.get(i).equals(EvalVariableRefProbeData.Status.MATCHED.getCode())) {
                evalVariableRefProbeDataList.get(i).setStatus(EvalVariableRefProbeData.Status.MATCHED.getCode());
            }
        }

        evalVariableRef.setProgress(EvalVariableRef.Progress.MARKING.getCode());
        transactionTemplate.execute(status -> {
            evalVariableRefProbeDataService.updateBatchById(evalVariableRefProbeDataList);
            return SqlHelper.retBool(getBaseMapper().updateById(evalVariableRef));
        });

        return startMark(evalVariableRef, evalAlgorithmRefMap, evalRecord, evalTask);
    }

    private String startMark(EvalVariableRef evalVariableRef, Map<String, EvalAlgorithmRef> evalAlgorithmRefMap, EvalRecord evalRecord, EvalTask evalTask) {
        EvalAlgorithmRef evalAlgorithmRef = evalAlgorithmRefMap.get(evalVariableRef.getFormatAlgorithmId());

        if (!evalVariableRef.getProgress().equals(EvalVariableRef.Progress.MARKING.getCode())) {
            return null;
        }
        List<EvalVariableRefProbeDataContext> evalVariableRefProbeDataContextList = evalVariableRefProbeDataService
                .variableProbeDataResult(evalVariableRef.getResourceId());

        MarkData data = new MarkData();
        // 构建评分算法数据
        data.setBaseTime(evalVariableRefProbeDataContextList.get(1).getTime().getTime());
        data.setData(Lists.newArrayList());
        evalVariableRefProbeDataContextList.forEach(variableRefProbeDataContext -> {
            MarkItemData itemData = new MarkItemData();
            itemData.setT(variableRefProbeDataContext.getTime().getTime() - data.getBaseTime());
            itemData.setV(variableRefProbeDataContext.getFormattedValue());
            itemData.setM(variableRefProbeDataContext.getStatus());

            data.getData().add(itemData);
        });

        // 运行匹配算法
        Integer score;
        evalVariableRef.setMarkStartTime(new Date());
        try {
            score = remotePythonService.markPerform(
                    evalVariableRef.getMarkAlgorithmId(),
                    evalAlgorithmRef.getCode(),
                    data,
                    evalAlgorithmRefService.buildOtherArguments(evalVariableRef.getFormatParams())
            );
        } catch (RemoteServiceException e) {
            return exceptionBefore(
                    evalVariableRef, evalRecord,
                    String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.VARIABLE_MARK_EXECUTE_FAILED), evalVariableRef.getName())
            );
        }

        evalVariableRef.setMarkEndTime(new Date());

        evalVariableRef.setScore(score);
        evalVariableRef.setProgress(EvalVariableRef.Progress.END.getCode());
        getBaseMapper().updateById(evalVariableRef);

        return null;
    }

    @Override
    public EvalVariableGraphRsp graph(String variableId) {
        EvalVariableGraphRsp result = new EvalVariableGraphRsp();
        result.setTime(Lists.newArrayList());
        result.setValue(Lists.newArrayList());
        result.setIsMatched(Lists.newArrayList());

        evalVariableRefProbeDataService.lambdaQuery().eq(EvalVariableRefProbeData::getVariableId, variableId).list().forEach(item -> {
            result.getTime().add(item.getTime());
            // todo 仅判断一次
            if (StringUtils.isNumeric(item.getFormattedValue())) {
                result.getValue().add(Integer.parseInt(item.getFormattedValue()));
                result.getIsMatched().add(item.getStatus().equals(EvalVariableRefProbeData.Status.MATCHED.getCode()));
            } else {
                boolean isMatched = item.getStatus().equals(EvalVariableRefProbeData.Status.MATCHED.getCode());
                result.getValue().add(isMatched ? 1 : 0);
                result.getIsMatched().add(isMatched);
            }
        });
        return result;
    }

    @Override
    public ListRsp<EvalVariableDataPageRsp> originData(Page page, String variableId) {
        return evalVariableRefProbeDataService.variableProbeData(page, variableId);
    }
}
