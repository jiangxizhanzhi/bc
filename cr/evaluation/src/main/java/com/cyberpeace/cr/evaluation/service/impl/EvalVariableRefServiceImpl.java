package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableGraphRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalVariableRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalProbeDataService;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefProbeDataService;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefService;
import com.cyberpeace.cr.evaluation.service.context.EvalVariableRefProbeDataContext;
import com.cyberpeace.integratation.model.evaluation.*;
import com.cyberpeace.integratation.service.RemotePythonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;
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

    // todo 任一流程执行失败, 单个变量项目计算结束
    @Override
    public Boolean startFormat(EvalVariableRef evalVariableRef, EvalAlgorithmRef evalAlgorithmRef, EvalRecord evalRecord, EvalTask evalTask) {
        if (!evalVariableRef.getProgress().equals(EvalVariableRef.Progress.FORMATTING.getCode())) {
            return true;
        }

        // 变量项探针采集数据  todo 分页执行
        List<EvalProbeData> evalProbeDataList = evalProbeDataService.lambdaQuery()
                .eq(EvalProbeData::getSceneId, evalTask.getMpSceneId())
                .eq(EvalProbeData::getNodeId, evalVariableRef.getDeviceId())
                .eq(EvalProbeData::getProbeId, evalVariableRef.getProbeId())
                .ge(EvalProbeData::getTime, evalRecord.getStartTime())
                .le(EvalProbeData::getTime, evalRecord.getEndTime())
                .list();

        // 构建格式化算法数据
        FormatData data = new FormatData();
        data.setData(evalProbeDataList.stream().map(EvalProbeData::getProbeData).collect(Collectors.toList()));

        // todo "算法其它入参"暂时为null, 无可用数据
        // 运行匹配算法
        evalVariableRef.setFormatStartTime(new Date());
        List<String> formatReturn = remotePythonService.formatPerform(null, evalAlgorithmRef.getResourceId(),
                evalAlgorithmRef.getCode(), data, null);
        evalVariableRef.setFormatEndTime(new Date());
        // todo return错误信息处理

        List<EvalVariableRefProbeData> evalVariableRefProbeDataList = Lists.newArrayList();
        for (int i = 0; i < formatReturn.size(); i++) {
            EvalVariableRefProbeData object = new EvalVariableRefProbeData();
            object.setProbeDataId(evalProbeDataList.get(i).getId());
            object.setVariableId(evalVariableRef.getResourceId());
            object.setFormattedValue(formatReturn.get(i));
            object.setStatus(EvalVariableRefProbeData.Status.NONE.getCode());

            evalVariableRefProbeDataList.add(object);
        }

        // todo 执行无误, 更新进度
        evalVariableRef.setProgress(EvalVariableRef.Progress.MATCHING.getCode());

        return transactionTemplate.execute(status -> {
            evalVariableRefProbeDataService.saveBatch(evalVariableRefProbeDataList);
            return SqlHelper.retBool(getBaseMapper().updateById(evalVariableRef));
        });
    }

    @Override
    public Boolean startMatch(EvalVariableRef evalVariableRef, EvalAlgorithmRef evalAlgorithmRef, EvalRecord evalRecord, EvalTask evalTask) {
        if (!evalVariableRef.getProgress().equals(EvalVariableRef.Progress.MATCHING.getCode())) {
            return true;
        }

        List<EvalVariableRefProbeDataContext> evalVariableRefProbeDataContextList = evalVariableRefProbeDataService
                .variableProbeDataResult(evalVariableRef.getResourceId());
        MatchData data = new MatchData();
        // todo 时间范围内无采集数据情况
        if (evalVariableRefProbeDataContextList.isEmpty()) {
            throw new BusinessException();
        }

        // 构建匹配算法数据
        data.setBaseTime(evalVariableRefProbeDataContextList.get(1).getTime().getTime());
        data.setData(Lists.newArrayList());
        evalVariableRefProbeDataContextList.forEach(variableRefProbeDataContext -> {
            MatchItemData itemData = new MatchItemData();
            itemData.setT(variableRefProbeDataContext.getTime().getTime() - data.getBaseTime());
            itemData.setV(variableRefProbeDataContext.getFormattedValue());
        });

        // 运行匹配算法
        evalVariableRef.setMatchStartTime(new Date());
        List<Integer> matchReturn = remotePythonService.matchPerform(
                null, evalAlgorithmRef.getResourceId(), evalAlgorithmRef.getCode(), data, null);
        evalVariableRef.setMatchEndTime(new Date());

        List<EvalVariableRefProbeData> evalVariableRefProbeDataList = evalVariableRefProbeDataService.lambdaQuery()
                .eq(EvalVariableRefProbeData::getVariableId, evalVariableRef.getResourceId())
                .list();

        // todo 执行失败, 返回值异常
        if (matchReturn.size() != evalVariableRefProbeDataList.size()) {
            throw new BusinessException();
        }

        for (int i = 0; i < evalVariableRefProbeDataList.size(); i++) {
            // 避免返回值存在[0, 1]外的值
            if (matchReturn.get(i).equals(EvalVariableRefProbeData.Status.MATCHED.getCode())) {
                evalVariableRefProbeDataList.get(i).setStatus(EvalVariableRefProbeData.Status.MATCHED.getCode());
            }
        }

        evalVariableRef.setProgress(EvalVariableRef.Progress.MARKING.getCode());
        return transactionTemplate.execute(status -> {
            evalVariableRefProbeDataService.updateBatchById(evalVariableRefProbeDataList);
            return SqlHelper.retBool(getBaseMapper().updateById(evalVariableRef));
        });
    }

    @Override
    public Boolean startMark(EvalVariableRef evalVariableRef, EvalAlgorithmRef evalAlgorithmRef, EvalRecord evalRecord, EvalTask evalTask) {
        if (!evalVariableRef.getProgress().equals(EvalVariableRef.Progress.MARKING.getCode())) {
            return true;
        }
        List<EvalVariableRefProbeDataContext> evalVariableRefProbeDataContextList = evalVariableRefProbeDataService
                .variableProbeDataResult(evalVariableRef.getResourceId());

        MarkData data = new MarkData();
        // todo 时间范围内无采集数据情况
        if (evalVariableRefProbeDataContextList.isEmpty()) {
            throw new BusinessException();
        }

        // 构建评分算法数据
        data.setBaseTime(evalVariableRefProbeDataContextList.get(1).getTime().getTime());
        data.setData(Lists.newArrayList());
        evalVariableRefProbeDataContextList.forEach(variableRefProbeDataContext -> {
            MarkItemData itemData = new MarkItemData();
            itemData.setT(variableRefProbeDataContext.getTime().getTime() - data.getBaseTime());
            itemData.setV(variableRefProbeDataContext.getFormattedValue());
            itemData.setM(variableRefProbeDataContext.getStatus());
        });

        // 运行匹配算法
        evalVariableRef.setMarkStartTime(new Date());
        Integer score = remotePythonService.markPerform(null, evalVariableRef.getMarkAlgorithmId(), evalAlgorithmRef.getCode(), data, null);
        evalVariableRef.setMarkEndTime(new Date());

        // todo 分值有效, 更新
        evalVariableRef.setScore(score);
        evalVariableRef.setProgress(EvalVariableRef.Progress.END.getCode());

        return SqlHelper.retBool(getBaseMapper().updateById(evalVariableRef));
    }

    @Override
    public EvalVariableGraphRsp graph(String variableId) {
        EvalVariableGraphRsp result = new EvalVariableGraphRsp();
        result.setTime(Lists.newArrayList());
        result.setValue(Lists.newArrayList());
        result.setIsMatched(Lists.newArrayList());

        evalVariableRefProbeDataService.lambdaQuery().eq(EvalVariableRefProbeData::getVariableId, variableId).list().forEach(item -> {
            result.getTime().add(item.getTime());
            result.getValue().add(item.getFormattedValue());
            result.getIsMatched().add(item.getStatus().equals(EvalVariableRefProbeData.Status.MATCHED.getCode()));
        });
        return result;
    }

    @Override
    public ListRsp<EvalVariableDataPageRsp> originData(Page page, String variableId) {
        return evalVariableRefProbeDataService.variableProbeData(page, variableId);
    }
}
